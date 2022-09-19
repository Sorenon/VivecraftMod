package org.vivecraft.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.events.client.ClientGuiEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import org.vivecraft.IPlatformService;
import org.vivecraft.Xplat;

import java.nio.file.Path;

public class FabricPlatformService implements IPlatformService {

    public boolean isModLoaded(String name) {
        return FabricLoader.getInstance().isModLoaded(name);
    }

    public Path getConfigPath(String fileName) {
        return FabricLoader.getInstance().getConfigDir().resolve(fileName);
    }

    public boolean isDedicatedServer() {
        return FabricLoader.getInstance().getEnvironmentType().equals(EnvType.SERVER);
    }

    public String getModloader() {
        return "fabric";
    }

    public String getModVersion() {
        return FabricLoader.getInstance().getModContainer("vivecraft").get().getMetadata().getVersion().getFriendlyString();
    }

    @Override
    public void drawScreen(Screen screen, PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (Xplat.isModLoaded("fabric")) {
            ScreenEvents.beforeRender(screen).invoker().beforeRender(screen, poseStack, mouseX, mouseY, partialTick);
        }
        if (Xplat.isModLoaded("architectury")) {
            ClientGuiEvent.RENDER_PRE.invoker().render(screen, poseStack, mouseX, mouseY, partialTick);
        }
        screen.render(poseStack, mouseX, mouseY, partialTick);
        if (Xplat.isModLoaded("fabric")) {
            ScreenEvents.afterRender(screen).invoker().afterRender(screen, poseStack, mouseX, mouseY, partialTick);
        }
        if (Xplat.isModLoaded("architectury")) {
            ClientGuiEvent.RENDER_POST.invoker().render(screen, poseStack, mouseX, mouseY, partialTick);
        }
    }
}