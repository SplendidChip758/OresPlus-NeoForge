package com.splendidchip.oresplus.block.entity;

import com.splendidchip.oresplus.block.ModBlocks;
//import com.splendidchip.oresplus.screen.custom.SmelterMenu;
import com.splendidchip.oresplus.recipe.ModRecipes;
import com.splendidchip.oresplus.recipe.simpleKiln.SimpleKilnRecipe;
import com.splendidchip.oresplus.recipe.smelter.SmelterRecipe;
import com.splendidchip.oresplus.recipe.smelter.SmelterRecipeInput;
import com.splendidchip.oresplus.screen.custom.SmelterMenu;
import com.splendidchip.oresplus.util.MultiblockStructureValidator;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SmelterControllerBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    public final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int FLUX_SLOT = 2;
    private static final int FUEL_SLOT = 3;
    private static final int OUTPUT_SLOT = 4;

    protected final ContainerData data;
    private int burnTime = 0;
    private int burnTimeTotal = 200;
    private int cookTime = 0;
    private int cookTimeTotal = 200;
    private boolean isStructureValid = false;

    private final Reference2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed;

    public SmelterControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SMELTER_CONTROLLER_BLOCK_ENTITY.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> burnTime;
                    case 1 -> cookTime;
                    case 2 -> cookTimeTotal;
                    case 3 -> burnTimeTotal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> burnTime = value;
                    case 1 -> cookTime = value;
                    case 2 -> cookTimeTotal = value;
                    case 3 -> burnTimeTotal = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };

        this.recipesUsed = new Reference2IntOpenHashMap<>();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.oresplus.smelter_controller_block");
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        if (!validateStructure()) {
            player.displayClientMessage(Component.translatable("block.oresplus.smelter_invalid_structure"), true);
            return null;
        }

        return new SmelterMenu(id, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("burnTime", burnTime);
        tag.putInt("burnTimeTotal", burnTimeTotal);
        tag.putInt("cookTime", cookTime);
        tag.putInt("cookTimeTotal", cookTimeTotal);
        tag.putBoolean("structureValid", isStructureValid);
        CompoundTag recipeTag = new CompoundTag();
        recipesUsed.forEach((key, count) -> recipeTag.putInt(key.location().toString(), count));
        tag.put("RecipesUsed", recipeTag);
    }

    @Override protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        burnTime = tag.getInt("burnTime");
        burnTimeTotal = tag.getInt("burnTimeTotal");
        cookTime = tag.getInt("cookTime");
        cookTimeTotal = tag.getInt("cookTimeTotal");
        isStructureValid = tag.getBoolean("structureValid");
        CompoundTag recipeTag = tag.getCompound("RecipesUsed");
        for (String key : recipeTag.getAllKeys()) {
            ResourceLocation id = ResourceLocation.tryParse(key);
            if (id != null) {
                ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE, id);
                recipesUsed.put(recipeKey, tag.getInt(key));
            }
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SmelterControllerBlockEntity blockEntity) {
        boolean wasBurning = blockEntity.burnTime > 0;
        boolean changed = false;

        if (blockEntity.burnTime > 0) {
            blockEntity.burnTime--;
        }

        blockEntity.isStructureValid = blockEntity.validateStructure();

        boolean canSmelt = blockEntity.hasRecipe() && blockEntity.isStructureValid;

        ItemStack fuelStack = blockEntity.itemHandler.getStackInSlot(FUEL_SLOT);

        if (blockEntity.burnTime == 0 && !fuelStack.isEmpty() && canSmelt) {
            int burn = blockEntity.getBurnTime(fuelStack);
            if (burn > 0) {
                blockEntity.burnTime = blockEntity.burnTimeTotal = burn;
                fuelStack.shrink(1);
                changed = true;
            }
        }

        if (blockEntity.burnTime > 0 && canSmelt) {
            blockEntity.cookTime++;
            blockEntity.cookTimeTotal = 200; // Placeholder, should pull from recipe
            if (blockEntity.cookTime >= blockEntity.cookTimeTotal) {
                blockEntity.cookTime = 0;
                blockEntity.craftItem(); // Placeholder logic
                changed = true;
            }
        } else {
            blockEntity.cookTime = 0;
        }

        if (wasBurning != (blockEntity.burnTime > 0)) {
            changed = true;
            level.setBlock(pos, state.setValue(BlockStateProperties.LIT, blockEntity.burnTime > 0), 3);
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private int getBurnTime(ItemStack fuel) {
        return fuel.getBurnTime(null, level.fuelValues()); // Use your own recipe type when ready
    }

    private void craftItem() {
        Optional<RecipeHolder<SmelterRecipe>> match = getCurrentRecipe();
        if (match.isEmpty()) return;

        SmelterRecipe recipe = match.get().value();
        ItemStack input1 = itemHandler.getStackInSlot(INPUT_SLOT_1);
        ItemStack input2 = itemHandler.getStackInSlot(INPUT_SLOT_2);
        ItemStack flux = itemHandler.getStackInSlot(FLUX_SLOT);
        ItemStack output = itemHandler.getStackInSlot(OUTPUT_SLOT);
        ItemStack result = recipe.getResult().copy();

        // Output slot check
        if (!output.isEmpty() && (!ItemStack.isSameItemSameComponents(output, result) || output.getCount() + result.getCount() > output.getMaxStackSize())) {
            return;
        }

        // Handle flux
        if (recipe.getFlux().isPresent() && !recipe.getFlux().get().test(flux)) return;
        if (recipe.getFlux().isPresent()) itemHandler.extractItem(FLUX_SLOT, 1, false);

        // Consume input(s)
        if (recipe.getInput2().isEmpty()) {
            if (recipe.getInput1().test(input1)) {
                itemHandler.extractItem(INPUT_SLOT_1, 1, false);
            } else if (recipe.getInput1().test(input2)) {
                itemHandler.extractItem(INPUT_SLOT_2, 1, false);
            } else {
                return;
            }
        } else {
            Ingredient inputA = recipe.getInput1();
            Ingredient inputB = recipe.getInput2().get();
            boolean forwardMatch = inputA.test(input1) && inputB.test(input2);
            boolean reverseMatch = inputA.test(input2) && inputB.test(input1);

            if (forwardMatch || reverseMatch) {
                itemHandler.extractItem(INPUT_SLOT_1, 1, false);
                itemHandler.extractItem(INPUT_SLOT_2, 1, false);
            } else {
                return;
            }
        }

        // Insert result
        if (output.isEmpty()) {
            itemHandler.setStackInSlot(OUTPUT_SLOT, result);
        } else {
            output.grow(result.getCount());
            itemHandler.setStackInSlot(OUTPUT_SLOT, output);
        }
        setRecipeUsed(match.get());
    }

    public void setRecipeUsed(@Nullable RecipeHolder<?> recipeHolder) {
        if (recipeHolder != null) {
            ResourceKey<Recipe<?>> key = recipeHolder.id();
            this.recipesUsed.addTo(key, 1);
        }
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
        player.awardRecipes(list);

        for (RecipeHolder<?> recipeHolder : list) {
            if (recipeHolder != null) {
                player.triggerRecipeCrafted(recipeHolder, List.of(this.itemHandler.getStackInSlot(OUTPUT_SLOT)));
            }
        }
        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 popVec) {
        List<RecipeHolder<?>> result = new ArrayList<>();

        for (Reference2IntMap.Entry<ResourceKey<Recipe<?>>> entry : recipesUsed.reference2IntEntrySet()) {
            ResourceKey<Recipe<?>> key = entry.getKey();
            int count = entry.getIntValue();

            level.getServer().getRecipeManager().byKey(key).ifPresent(holder -> {
                result.add(holder);

                float xpPer = 0.0f;
                if (holder.value() instanceof SmelterRecipe smelterRecipe) {
                    xpPer = smelterRecipe.getExperience();
                }
                createExperience(level, popVec, count, xpPer);
            });
        }

        return result;
    }

    private static void createExperience(ServerLevel level, Vec3 pos, int count, float xpPer) {
        int i = Mth.floor((float) count * xpPer);
        float f = Mth.frac((float) count * xpPer);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }
        ExperienceOrb.award(level, pos, i);
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<SmelterRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack output = recipe.get().value().getResult();
        return canInsertAmountIntoOutputSlot(output.getCount())
                && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeHolder<SmelterRecipe>> getCurrentRecipe() {
        return level.getServer()
                .getRecipeManager()
                .getRecipeFor(
                        ModRecipes.SMELTER_TYPE.get(),
                        new SmelterRecipeInput(
                                itemHandler.getStackInSlot(INPUT_SLOT_1),
                                itemHandler.getStackInSlot(INPUT_SLOT_2),
                                itemHandler.getStackInSlot(FLUX_SLOT)
                        ),
                        level
                );
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize() >=
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count;
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack stack) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty()
                || (ItemStack.isSameItemSameComponents(itemHandler.getStackInSlot(OUTPUT_SLOT), stack)
                && itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + stack.getCount()
                <= stack.getMaxStackSize());
    }


    public boolean validateStructure() {
        return MultiblockStructureValidator.validateHollowCubeWithControllerOnFront(
                level, worldPosition, ModBlocks.REFRACTORY_BRICKS.get()
        );
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override public int getContainerSize() {
        return itemHandler.getSlots();
    }

    @Override public boolean isEmpty() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override public ItemStack getItem(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override public ItemStack removeItem(int slot, int amount) {
        return itemHandler.extractItem(slot, amount, false);
    }

    @Override public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = itemHandler.getStackInSlot(slot);
        itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override public void setItem(int slot, ItemStack stack) {
        itemHandler.setStackInSlot(slot, stack);
    }

    @Override public boolean stillValid(Player player) {
        return player.distanceToSqr(worldPosition.getCenter()) <= 64;
    }

    @Override public void clearContent() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    // WorldlyContainer
    @Override public boolean canPlaceItem(int index, ItemStack itemStack) {
        return index != OUTPUT_SLOT;
    }

    @Override public int[] getSlotsForFace(Direction side) {
        return switch (side) {
            case DOWN -> new int[]{OUTPUT_SLOT};
            case UP -> new int[]{INPUT_SLOT_1, INPUT_SLOT_2};
            default -> new int[]{FUEL_SLOT};
        };
    }

    @Override public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(index, itemStack);
    }

    @Override public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == OUTPUT_SLOT;
    }
}
