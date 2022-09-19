package org.vivecraft.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import org.vivecraft.IPlatformService;

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
        screen.render(poseStack, mouseX, mouseY, partialTick);
    }
}