package org.vivecraft.client_vr.render;

import com.mojang.math.Axis;
import org.vivecraft.client_vr.ClientDataHolderVR;
import org.vivecraft.api.client.VRData;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.vivecraft.client_xr.XRState;


public class XRCamera extends Camera
{
    public void setup(BlockGetter pLevel, Entity pRenderViewEntity, boolean pThirdPerson, boolean pThirdPersonReverse, float pPartialTicks)
    {
        if (!XRState.isXr) {
            super.setup(pLevel, pRenderViewEntity, pThirdPerson, pThirdPersonReverse, pPartialTicks);
            return;
        }
        this.initialized = true;
        this.level = pLevel;
        this.entity = pRenderViewEntity;
        ClientDataHolderVR dataholder = ClientDataHolderVR.getInstance();
        RenderPass renderpass = dataholder.currentPass;

        VRData.VRDevicePose eye = dataholder.vrPlayer.vrdata_world_render.getEye(renderpass);
        this.setPosition(eye.getPosition());
        this.xRot = -eye.getPitch();
        this.yRot = eye.getYaw();
        this.forwards.set((float)eye.getDirection().x, (float)eye.getDirection().y, (float)eye.getDirection().z);
        Vec3 vec3 = eye.getCustomVector(new Vec3(0.0D, 1.0D, 0.0D));
        this.up.set((float)vec3.x, (float)vec3.y, (float)vec3.z);
        eye.getCustomVector(new Vec3(1.0D, 0.0D, 0.0D));
        this.left.set((float)vec3.x, (float)vec3.y, (float)vec3.z);
        this.rotation.set(0.0F, 0.0F, 0.0F, 1.0F);
        this.rotation.mul(Axis.YP.rotationDegrees(-this.yRot));
        this.rotation.mul(Axis.XP.rotationDegrees(this.xRot));
    }

    // NotFixed
//    public void tick()
//    {
//    }

    //SorenonTODO Investigate
    public boolean isDetached()
    {
        return false;
    }
}