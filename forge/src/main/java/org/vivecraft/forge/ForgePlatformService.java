package org.vivecraft.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import org.vivecraft.IPlatformService;

import java.nio.file.Path;

public class ForgePlatformService implements IPlatformService {

    public boolean isModLoaded(String name) {
        return FMLLoader.getLoadingModList().getModFileById(name) != null;
    }

    public Path getConfigPath(String fileName) {
        return FMLPaths.CONFIGDIR.get().resolve(fileName);
    }

    public boolean isDedicatedServer() {
        return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
    }

    public String getModloader() {
        return "forge";
    }
    public String getModVersion() {
        return FMLLoader.getLoadingModList().getModFileById("vivecraft").versionString();
    }

    @Override
    public void drawScreen(Screen screen, PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        ForgeHooksClient.drawScreen(screen, poseStack, mouseX, mouseY, partialTick);
    }
}
