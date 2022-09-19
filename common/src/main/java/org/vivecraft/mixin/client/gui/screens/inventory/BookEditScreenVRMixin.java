package org.vivecraft.mixin.client.gui.screens.inventory;

import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.vivecraft.client.vr.gameplay.screenhandlers.KeyboardHandler;

@Mixin(BookEditScreen.class)
public class BookEditScreenVRMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/BookEditScreen;updateButtonVisibility()V", shift = At.Shift.BEFORE), method = "init")
    public void overlay(CallbackInfo ci) {
        KeyboardHandler.setOverlayShowing(true);
    }
}
