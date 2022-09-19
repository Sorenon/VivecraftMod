package org.vivecraft.mixin.client.renderer.entity;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.vivecraft.VRState;
import org.vivecraft.client.ClientDataHolder;
import org.vivecraft.client.render.RenderPass;
import org.vivecraft.client.render.VRArmRenderer;
import org.vivecraft.extensions.EntityRenderDispatcherExtension;
import org.vivecraft.extensions.LevelRendererExtension;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherVRMixin implements ResourceManagerReloadListener, EntityRenderDispatcherExtension {

    @Unique
    private VRArmRenderer armRenderer;
    @Unique
    public final Map<String, VRArmRenderer> armSkinMap = new HashMap<>();
    @Shadow
    public Camera camera;
    @Shadow
    private Quaternion cameraOrientation;

    @Inject(at = @At("HEAD"), method = "cameraOrientation", cancellable = true)
    public void cameraOrientation(CallbackInfoReturnable<Quaternion> cir) {
        if (ClientDataHolder.getInstance().currentPass == RenderPass.GUI || !VRState.vrRenderPass) {
            cir.setReturnValue(this.camera.rotation());
            return;
        }
        Entity entity = ((LevelRendererExtension) Minecraft.getInstance().levelRenderer).getRenderedEntity();
        if (entity == null) {
            cir.setReturnValue(this.camera.rotation());
        } else {
            /*
             * Change the orientation so 2D texture entities face the player's eyes
             */
            Vec3 vec3 = ClientDataHolder.getInstance().vrPlayer.getVRDataWorld().getEye(RenderPass.CENTER).getPosition();
            if (ClientDataHolder.getInstance().currentPass == RenderPass.THIRD || ClientDataHolder.getInstance().currentPass == RenderPass.CAMERA) {
                vec3 = ClientDataHolder.getInstance().vrPlayer.getVRDataWorld().getEye(ClientDataHolder.getInstance().currentPass).getPosition();
            }
            Vec3 vec31 = entity.position().add(0.0D, (double) (entity.getBbHeight() / 2.0F), 0.0D).subtract(vec3).normalize();
            this.cameraOrientation.set(0.0F, 0.0F, 0.0F, 1.0F);
            this.cameraOrientation.mul(Vector3f.YP.rotationDegrees((float) (-Math.toDegrees(Math.atan2(-vec31.x, vec31.z)))));
            this.cameraOrientation.mul(Vector3f.XP.rotationDegrees((float) (-Math.toDegrees(Math.asin(vec31.y / vec31.length())))));
            cir.setReturnValue(this.cameraOrientation);
        }
    }

    //TODO move this out of mixin
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderers;createPlayerRenderers(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)Ljava/util/Map;", shift = At.Shift.AFTER),
            method = "onResourceManagerReload(Lnet/minecraft/server/packs/resources/ResourceManager;)V", locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void reload(ResourceManager resourceManager, CallbackInfo ci, EntityRendererProvider.Context context) {
        this.armRenderer = new VRArmRenderer(context, false);
        this.armSkinMap.put("default", this.armRenderer);
        this.armSkinMap.put("slim", new VRArmRenderer(context, true));
    }

    /**
     * Change the orientation so 2D texture entities face the player's eyes
     */
    //TODO move this out of mixin
    @Override
    public Quaternion getCameraOrientationOffset(float offset) {
        if (ClientDataHolder.getInstance().currentPass == RenderPass.GUI || GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_J) != 0) {
            return this.camera.rotation();
        } else {
            Entity entity = ((LevelRendererExtension) Minecraft.getInstance().levelRenderer).getRenderedEntity();
            if (entity == null) {
                return this.camera.rotation();
            } else {
                Vec3 vec3 = ClientDataHolder.getInstance().vrPlayer.getVRDataWorld().getEye(RenderPass.CENTER).getPosition();
                if (ClientDataHolder.getInstance().currentPass == RenderPass.THIRD || ClientDataHolder.getInstance().currentPass == RenderPass.CAMERA) {
                    vec3 = ClientDataHolder.getInstance().vrPlayer.getVRDataWorld().getEye(ClientDataHolder.getInstance().currentPass).getPosition();
                }
                Vec3 vec31 = entity.position().add(0.0D, (double) (entity.getBbHeight() + offset), 0.0D).subtract(vec3).normalize();
                this.cameraOrientation.set(0.0F, 0.0F, 0.0F, 1.0F);
                this.cameraOrientation.mul(Vector3f.YP.rotationDegrees((float) (-Math.toDegrees(Math.atan2(-vec31.x, vec31.z)))));
                this.cameraOrientation.mul(Vector3f.XP.rotationDegrees((float) (-Math.toDegrees(Math.asin(vec31.y / vec31.length())))));
                return this.cameraOrientation;
            }
        }
    }

    public Map<String, VRArmRenderer> getArmSkinMap() {
        return armSkinMap;
    }
}
