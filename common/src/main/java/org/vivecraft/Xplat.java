package org.vivecraft;

import java.nio.file.Path;

public interface Xplat {
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