package org.vivecraft.mixin.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.vivecraft.CommonDataHolder;
import org.vivecraft.client.render.PlayerModelController;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {


    @Shadow
    protected abstract int getFramerateLimit();

    @ModifyConstant(method = "createTitle", constant = @Constant(stringValue = "Minecraft"))
    private String title(String s) {
        return CommonDataHolder.getInstance().minecriftVerString;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/SoundManager;tick(Z)V", shift = Shift.BEFORE), method = "tick()V", cancellable = true)
    public void music(CallbackInfo info) {
        PlayerModelController.getInstance().tick();
    }


}
