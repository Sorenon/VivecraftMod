package org.vivecraft.mixin.client.blaze3d.platform;

import org.vivecraft.mixin.client.blaze3d.systems.RenderSystemAccessor;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.*;

import static com.mojang.blaze3d.platform.GlStateManager.BLEND;
import static com.mojang.blaze3d.platform.GlStateManager.glBlendFuncSeparate;

@Mixin(GlStateManager.class)
public class GlStateManagerVRMixin {

    //Change the limit of textures to 32
    @ModifyArg(at = @At(value = "INVOKE", target = "Ljava/util/stream/IntStream;range(II)Ljava/util/stream/IntStream;"), index = 1, method = "<clinit>")
    private static int size(int i) {
        return 32;
    }

    //TODO (Sorenon) figure out how why this is needed
    //Change the limit of textures to 32
    @ModifyConstant(constant = @Constant(intValue = 12),method = "_getTextureId", remap = false)
    private static int properId(int i) {
        return RenderSystemAccessor.getShaderTextures().length;
    }

    //TODO (Sorenon) figure out what this fixes, it's not the GUI thing I thought it was
//    /**
//     * @author
//     * @reason
//     */
//    // do remap because of forge
//    @Overwrite
//    public static void _blendFuncSeparate(int i, int j, int k, int l) {
//        // VIVECRAFT: correct bad blend function that trashes alpha channel
//        RenderSystem.assertOnRenderThread();
//        if (i == GlStateManager.SourceFactor.SRC_ALPHA.value
//            && j == GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value
//            && k == GlStateManager.SourceFactor.ONE.value
//            && l == GlStateManager.DestFactor.ZERO.value) {
//            l = GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value;
//        }
//        if (i != BLEND.srcRgb || j != BLEND.dstRgb || k != BLEND.srcAlpha || l != BLEND.dstAlpha) {
//            BLEND.srcRgb = i;
//            BLEND.dstRgb = j;
//            BLEND.srcAlpha = k;
//            BLEND.dstAlpha = l;
//            glBlendFuncSeparate(i, j, k, l);
//        }
//    }
}
