package org.vivecraft.mixin.client.blaze3d.platform;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.vivecraft.VRState;

@Mixin(Window.class)
public abstract class WindowVRMixin {


    @Shadow
    private int width;
    @Shadow
    private int height;
    @Final
    @Shadow
    private WindowEventHandler eventHandler;
    @Shadow
    private double guiScale;
    @Shadow
    private int guiScaledWidth;
    @Shadow
    private int guiScaledHeight;

    @Shadow
    public abstract int getScreenWidth();

    @Shadow
    public abstract int getScreenHeight();

    //TODO disable
    @Redirect(at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetFramebufferSizeCallback(JLorg/lwjgl/glfw/GLFWFramebufferSizeCallbackI;)Lorg/lwjgl/glfw/GLFWFramebufferSizeCallback;", remap = false),
            method = "<init>(Lcom/mojang/blaze3d/platform/WindowEventHandler;Lcom/mojang/blaze3d/platform/ScreenManager;Lcom/mojang/blaze3d/platform/DisplayData;Ljava/lang/String;Ljava/lang/String;)V")
    public GLFWFramebufferSizeCallback removebuffer(long l, GLFWFramebufferSizeCallbackI cl) {
        return null;
    }

    //TODO re-allow vsync when XR is disabled
    @ModifyVariable(method = "updateVsync", ordinal = 0, at = @At("HEAD"), argsOnly = true)
    boolean overwriteVsync(boolean v) {
        GLFW.glfwSwapInterval(0);
        return false;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;getWidth()I"), method = "onFramebufferResize(JII)V")
    public int widthCorrection(Window w) {
        return this.getScreenWidth();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;getHeight()I"), method = "onFramebufferResize(JII)V")
    public int heightCorrection(Window w) {
        return this.getScreenHeight();
    }

    @Inject(at = @At("HEAD"), method = "onResize(JII)V", cancellable = true)
    public void resize(long pWindowPointer, int p_85429_, int pWindowWidth, CallbackInfo info) {
        if (pWindowWidth * p_85429_ != 0) {
            this.width = p_85429_;
            this.height = pWindowWidth;
            this.eventHandler.resizeDisplay();
        }
        info.cancel();
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public int calculateScale(int pGuiScale, boolean bl) {
        int i;
        for (i = 1; i != pGuiScale && i < this.width && i < this.height && this.width / (i + 1) >= 320 && this.height / (i + 1) >= 240; ++i) {

        }

        if (bl && i % 2 != 0) {
//			++j;
        }

        return i;
    }


    /**
     * @author
     * @reason
     */
    @Overwrite
    public void setGuiScale(double pScaleFactor) {
        this.guiScale = pScaleFactor;

        int i = (int) ((double) this.width / pScaleFactor);
        this.guiScaledWidth = (double) this.width / pScaleFactor > (double) i ? i + 1 : i;
        int j = (int) ((double) this.height / pScaleFactor);
        this.guiScaledHeight = (double) this.height / pScaleFactor > (double) j ? j + 1 : j;
    }

    @Inject(method = "getWidth", at = @At("HEAD"), cancellable = true)
    void getWidth(CallbackInfoReturnable<Integer> cir) {
        if (isCustomFramebuffer()) {
            var mainTarget = Minecraft.getInstance().getMainRenderTarget();
            cir.setReturnValue(mainTarget.viewWidth);
        }
    }

    @Inject(method = "getHeight", at = @At("HEAD"), cancellable = true)
    void getHeight(CallbackInfoReturnable<Integer> cir) {
        if (isCustomFramebuffer()) {
            var mainTarget = Minecraft.getInstance().getMainRenderTarget();
            cir.setReturnValue(mainTarget.viewHeight);
        }
    }


    @Unique
    boolean isCustomFramebuffer() {
//		return mcxrGameRenderer.overrideWindowSize || (mcxrGameRenderer.isXrMode() && mcxrGameRenderer.reloadingDepth > 0);
        return VRState.overrideWindowSize;
    }
}
