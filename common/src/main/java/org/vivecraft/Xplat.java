package org.vivecraft;

import java.nio.file.Path;

public interface Xplat {
    /**
     * <p>
     * This must be a <b>public static</b> method. The platform-implemented solution must be placed under a
     * platform sub-package, with its class suffixed with {@code Impl}.
     * <p>
     * Example:
     * Expect: net.examplemod.ExampleExpectPlatform#getConfigDirectory()
     * Actual Fabric: net.examplemod.fabric.ExampleExpectPlatformImpl#getConfigDirectory()
     * Actual Forge: net.examplemod.forge.ExampleExpectPlatformImpl#getConfigDirectory()
     * <p>
     * <a href="https://plugins.jetbrains.com/plugin/16210-architectury">You should also get the IntelliJ plugin to help with @ExpectPlatform.</a>
     */

    static boolean isModLoaded(String name) {
        return IPlatformService.INSTANCE.isModLoaded(name);
    }

    static Path getConfigPath(String fileName) {
        return IPlatformService.INSTANCE.getConfigPath(fileName);
    }

    static boolean isDedicatedServer() {
        return IPlatformService.INSTANCE.isDedicatedServer();
    }

    static String getModloader() {
        return IPlatformService.INSTANCE.getModloader();
    }

    static String getModVersion() {
        return IPlatformService.INSTANCE.getModVersion();
    }
}
