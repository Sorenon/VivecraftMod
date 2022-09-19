package org.vivecraft.mixin.client.blaze3d.systems;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import static com.mojang.blaze3d.systems.RenderSystem.blendFuncSeparate;

@Mixin(RenderSystem.class)
public interface RenderSystemAccessor {

    // needs remap because of forge
    @Accessor
    public static int[] getShaderTextures(){
        return null;
    }
}
