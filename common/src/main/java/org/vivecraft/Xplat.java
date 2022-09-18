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
//    @ExpectPlatform
    static boolean isModLoaded(String name) {
        return TMP.SERVICE.isModLoaded(name);
    }

    //    @ExpectPlatform
    static Path getConfigPath(String fileName) {
        return TMP.SERVICE.getConfigPath(fileName);
    }

    //    @ExpectPlatform
    static boolean isDedicatedServer() {
        return TMP.SERVICE.isDedicatedServer();
    }

    //    @ExpectPlatform
    static String getModloader() {
        return TMP.SERVICE.getModloader();
    }

    //    @ExpectPlatform
    static String getModVersion() {
        return TMP.SERVICE.getModVersion();
    }
}
