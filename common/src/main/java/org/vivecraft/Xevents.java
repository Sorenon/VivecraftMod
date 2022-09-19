package org.vivecraft;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public interface Xevents {

    static boolean renderBlockOverlay(Player player, PoseStack mat, BlockState state, BlockPos pos) {
        return false;
    }

    static boolean renderWaterOverlay(Player player, PoseStack mat) {
        return false;
    }

    static boolean renderFireOverlay(Player player, PoseStack mat) {
        return false;
    }

    static void onRenderTickStart(float f) {

    }

    static void onRenderTickEnd(float f) {

    }

    static void drawScreen(Screen screen, PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        IPlatformService.INSTANCE.drawScreen(screen, poseStack, mouseX, mouseY, partialTick);
    }


}
