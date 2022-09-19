package org.vivecraft;

public class ModList {
    public static final boolean fabricApi = IPlatformService.INSTANCE.isModLoaded("fabric");
    public static final boolean sodium = IPlatformService.INSTANCE.isModLoaded("sodium");
    public static final boolean iris = IPlatformService.INSTANCE.isModLoaded("iris");
    public static final boolean rubidium = IPlatformService.INSTANCE.isModLoaded("rubidium");
    public static final boolean oculus = IPlatformService.INSTANCE.isModLoaded("oculus");
}
