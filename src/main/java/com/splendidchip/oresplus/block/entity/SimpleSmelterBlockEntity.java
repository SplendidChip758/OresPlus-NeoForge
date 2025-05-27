package com.splendidchip.oresplus.block.entity;

import com.splendidchip.oresplus.recipe.ModRecipes;
import com.splendidchip.oresplus.recipe.smelter.SmelterRecipe;
import com.splendidchip.oresplus.recipe.smelter.SmelterRecipeInput;
import com.splendidchip.oresplus.screen.custom.SimpleSmelterMenu;
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
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
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

public class SimpleSmelterBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {

    public final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int FLUX_SLOT = 1;
    private static final int FUEL_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;

    protected final ContainerData data;
    private int burnTime = 0;
    private int burnTimeTotal = 200;
    private int cookTime = 0;
    private int cookTimeTotal = 200;

    private final Reference2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed;

    public SimpleSmelterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SIMPLE_SMELTER_BLOCK_ENTITY.get(), pos, state);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> burnTime;
                    case 1 -> cookTime;
                    case 2 -> cookTimeTotal;
                    case 3 -> burnTimeTotal;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> burnTime = value;
                    case 1 -> cookTime = value;
                    case 2 -> cookTimeTotal = value;
                    case 3 -> burnTimeTotal = value;
                }
            }

            public int getCount() {
                return 4;
            }
        };

        this.recipesUsed = new Reference2IntOpenHashMap<>();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.oresplus.simple_smelter_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SimpleSmelterMenu(i, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("burnTime", burnTime);
        tag.putInt("burnTimeTotal", burnTimeTotal);
        tag.putInt("cookTime", cookTime);
        tag.putInt("cookTimeTotal", cookTimeTotal);
        CompoundTag recipeTag = new CompoundTag();
        recipesUsed.forEach((key, count) -> recipeTag.putInt(key.location().toString(), count));
        tag.put("RecipesUsed", recipeTag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        burnTime = tag.getInt("burnTime");
        burnTimeTotal = tag.getInt("burnTimeTotal");
        cookTime = tag.getInt("cookTime");
        cookTimeTotal = tag.getInt("cookTimeTotal");
        CompoundTag recipeTag = tag.getCompound("RecipesUsed");
        for (String key : recipeTag.getAllKeys()) {
            ResourceLocation id = ResourceLocation.tryParse(key);
            if (id != null) {
                ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE, id);
                recipesUsed.put(recipeKey, tag.getInt(key));
            }
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SimpleSmelterBlockEntity smelter) {
        boolean wasBurning = smelter.burnTime > 0;
        boolean stateChanged = false;

        if (smelter.burnTime > 0) smelter.burnTime--;

        ItemStack fuel = smelter.itemHandler.getStackInSlot(FUEL_SLOT);
        Optional<RecipeHolder<SmelterRecipe>> recipeOpt = smelter.getCurrentRecipe();

        if (smelter.burnTime == 0 && !fuel.isEmpty() && recipeOpt.isPresent() && smelter.hasRecipe()) {
            int burn = smelter.getBurnTime(fuel);
            if (burn > 0) {
                smelter.burnTime = smelter.burnTimeTotal = burn;
                fuel.shrink(1);
                stateChanged = true;
            }
        }

        if (smelter.burnTime > 0 && recipeOpt.isPresent() && smelter.hasRecipe()) {
            smelter.cookTime++;
            smelter.cookTimeTotal = recipeOpt.get().value().getCookTime();

            if (smelter.cookTime >= smelter.cookTimeTotal) {
                smelter.cookTime = 0;
                smelter.craftItem();
                stateChanged = true;
            }
        } else {
            smelter.cookTime = 0;
        }

        if (wasBurning != smelter.burnTime > 0) {
            stateChanged = true;
            level.setBlock(pos, state.setValue(BlockStateProperties.LIT, smelter.burnTime > 0), 3);
        }

        if (stateChanged) {
            setChanged(level, pos, state);
        }
    }

    private int getBurnTime(ItemStack fuel) {
        return fuel.getBurnTime(ModRecipes.SMELTER_TYPE.get(), level.fuelValues());
    }

    private void craftItem() {
        Optional<RecipeHolder<SmelterRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().getResult();

        itemHandler.extractItem(INPUT_SLOT, 1, false);
        itemHandler.extractItem(FLUX_SLOT, 1, false);
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(), itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
        setRecipeUsed(recipe.get());
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
                // Optional: You could pass an actual input list for display stats
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
        return canInsertAmountIntoOutputSlot(output.getCount()) &&
                canInsertItemIntoOutputSlot(output) &&
                !itemHandler.getStackInSlot(INPUT_SLOT).isEmpty() &&
                // If the recipe requires flux, check for it; otherwise, allow empty
                (!recipe.get().value().getFlux().isPresent() || !itemHandler.getStackInSlot(FLUX_SLOT).isEmpty());
    }

    private Optional<RecipeHolder<SmelterRecipe>> getCurrentRecipe() {
        return level.getServer().getRecipeManager()
                .getRecipeFor(ModRecipes.SMELTER_TYPE.get(),
                        new SmelterRecipeInput(
                                itemHandler.getStackInSlot(INPUT_SLOT),
                                ItemStack.EMPTY, // No input2 for simple smelter
                                itemHandler.getStackInSlot(FLUX_SLOT)),
                        level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
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

    @Override
    public boolean canPlaceItem(int index, ItemStack itemStack) {
        if (index == OUTPUT_SLOT) return false;
        if (index == FUEL_SLOT) {
            return itemStack.getBurnTime(ModRecipes.SIMPLE_KILN_TYPE.get(), level.fuelValues()) > 0;
        }
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return switch (side) {
            case DOWN -> new int[]{OUTPUT_SLOT};
            case UP -> new int[]{INPUT_SLOT};
            default -> new int[]{FUEL_SLOT, FLUX_SLOT};
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        if (index == INPUT_SLOT) return true;
        if (index == FUEL_SLOT) {
            return itemStack.getBurnTime(ModRecipes.SIMPLE_KILN_TYPE.get(), level.fuelValues()) > 0;
        }
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == OUTPUT_SLOT;
    }

    @Override
    public int getContainerSize() {
        return itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = itemHandler.extractItem(slot, amount, false);
        setChanged();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = itemHandler.getStackInSlot(slot);
        itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        itemHandler.setStackInSlot(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
