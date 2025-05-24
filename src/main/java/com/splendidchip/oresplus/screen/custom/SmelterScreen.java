package com.splendidchip.oresplus.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.splendidchip.oresplus.OresPlus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SmelterScreen extends AbstractContainerScreen<SmelterMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "textures/gui/smelter/smelter_gui.png");
    private static final ResourceLocation PROGRESS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "textures/gui/smelter/progress_arrow.png");
    private static final ResourceLocation FIRE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "textures/gui/smelter/lit_progress.png");

    public SmelterScreen(SmelterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(CoreShaders.POSITION_COLOR);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderType::guiTextured, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        int labelWidth = font.width("Flux");
        int fluxSlotX = leftPos + 25;
        int centeredX = fluxSlotX + 9 - labelWidth / 2;

        guiGraphics.drawString(this.font, "Flux", centeredX, topPos + 43, 0x404040, false);


        renderProgressArrow(guiGraphics, x, y);
        renderLitIndicator(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        int progress = menu.getScaledProgress();
        if (progress > 0) {
            guiGraphics.blit(RenderType::guiTextured, PROGRESS_TEXTURE,
                    x + 79, y + 34,
                    0, 0,
                    progress, 16,
                    24, 16
            );
        }
    }

    private void renderLitIndicator(GuiGraphics guiGraphics, int x, int y) {
        int lit = menu.getLitProgress();
        if (lit > 0) {
            guiGraphics.blit(RenderType::guiTextured,
                    FIRE_TEXTURE,
                    x + 57, y + 36 + (13 - lit),
                    0, 13 - lit,
                    14, lit,
                    14, 14
            );
        }
    }

    private void renderFuelTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        int relMouseX = mouseX - x;
        int relMouseY = mouseY - y;

        // Flame icon tooltip area
        if (relMouseX >= 57 && relMouseX < 57 + 14 && relMouseY >= 36 && relMouseY < 36 + 14) {
            int burnTime = menu.getBurnTime();
            int burnTimeTotal = menu.getBurnTimeTotal();

            guiGraphics.renderTooltip(this.font,
                    Component.translatable("tooltip.oresplus.burn_time", burnTime, burnTimeTotal),
                    mouseX, mouseY);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderFuelTooltip(guiGraphics, mouseX, mouseY);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}

