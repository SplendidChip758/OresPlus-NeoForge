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

public class SimpleKilnScreen extends AbstractContainerScreen<SimpleKilnMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "textures/gui/simple_kiln_block/simple_kiln_block_gui.png");
    private static final ResourceLocation PROGRESS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "textures/gui/simple_kiln_block/progress_arrow.png");
    private static final ResourceLocation FIRE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "textures/gui/simple_kiln_block/lit_progress.png");

    public SimpleKilnScreen(SimpleKilnMenu menu, Inventory playerInventory, Component title) {
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

        renderProgressArrow(guiGraphics, x, y);
        renderLitIndicator(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        int progress = menu.getScaledProgress();
        if (progress > 0) {
            guiGraphics.blit(RenderType::guiTextured, PROGRESS_TEXTURE,
                    x + 79, y + 34,   // position in GUI
                    0, 0,             // UV origin in texture
                    progress, 16,     // width, height to draw
                    24, 16            // full texture size
            );
        }
    }

    private void renderLitIndicator(GuiGraphics guiGraphics, int x, int y) {
        int lit = menu.getLitProgress();
        if (lit > 0) {
            guiGraphics.blit(RenderType::guiTextured,
                    FIRE_TEXTURE,
                    x + 57, y + 36 + (13 - lit),   // moves up as it burns
                    0, 13 - lit,                   // UV origin moves
                    14, lit,                       // width/height
                    14, 14                         // full texture
            );
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}

