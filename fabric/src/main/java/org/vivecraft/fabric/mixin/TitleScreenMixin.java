package org.vivecraft.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.vivecraft.client.ClientDataHolder;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Unique
    private static boolean initialized = false;

    @Inject(method = "render", at = @At("HEAD"))
    void onRender(PoseStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!initialized) {
            if (ClientDataHolder.getInstance().vrRenderer.isInitialized()) {
                //DataHolder.getInstance().menuWorldRenderer.init();
            }
            ClientDataHolder.getInstance().vr.postinit();
            initialized = true;
        }
    }
}
