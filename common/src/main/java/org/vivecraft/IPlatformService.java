package org.vivecraft;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface IPlatformService {

    //TODO create better getter
    IPlatformService INSTANCE = ServiceLoader.load(IPlatformService.class).findFirst().get();

    boolean isModLoaded(String name);

    Path getConfigPath(String fileName);

    boolean isDedicatedServer();

    String getModloader();

    String getModVersion();

    void drawScreen(Screen screen, PoseStack poseStack, int mouseX, int mouseY, float partialTick);

    boolean isModLoadedSuccess();

}