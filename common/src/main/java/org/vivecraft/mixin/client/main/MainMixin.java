package org.vivecraft.mixin.client.main;

import org.vivecraft.client.ClientDataHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.client.main.Main;

@Mixin(Main.class)
public class MainMixin {
	
	@Inject(at = @At(value = "INVOKE", target = "Ljoptsimple/OptionParser;allowsUnrecognizedOptions()V", remap = false), method = "main([Ljava/lang/String;)V", locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
	private static void options(String[] p_129642_, CallbackInfo callback, OptionParser optionparser) {
		optionparser.accepts("kiosk");
		optionparser.accepts("viewonly");
		optionparser.accepts("katvr");
		optionparser.accepts("infinadeck");
	}

	@Redirect(at = @At(value = "INVOKE", target = "Ljoptsimple/OptionParser;parse([Ljava/lang/String;)Ljoptsimple/OptionSet;", remap = false) , method = "main([Ljava/lang/String;)V", remap = false)
	private static OptionSet kiosk(OptionParser optionparser, String[] p_129642_) {
		OptionSet optionset = optionparser.parse(p_129642_);
		ClientDataHolder.kiosk = optionset.has("kiosk");
		
		if (ClientDataHolder.kiosk)
		{
			System.out.println("Setting kiosk");
		}
		
		if (ClientDataHolder.kiosk)
		{
			ClientDataHolder.viewonly = optionset.has("viewonly");
			
			if (ClientDataHolder.viewonly)
			{
				System.out.println("Setting viewonly");
			}
		}

		ClientDataHolder.katvr = optionset.has("katvr");
		ClientDataHolder.infinadeck = optionset.has("infinadeck");
		return optionset;
	}
}
