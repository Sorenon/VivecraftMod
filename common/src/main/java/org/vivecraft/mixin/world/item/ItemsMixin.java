package org.vivecraft.mixin.world.item;

import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Items.class)
public class ItemsMixin {

    @Inject(at = @At("HEAD"), method = "<clinit>")
    private static void addCrafting(CallbackInfo ci) {
        //RecipeSerializer.register("vivecraft:vivecraft_crafting", VivecraftCrafting.SERIALIZER);
    }
}
