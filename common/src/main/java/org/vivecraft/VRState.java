package org.vivecraft;

public class VRState {
    public static boolean isVR;

    public static boolean vrRenderPass = true;
    public static boolean overrideWindowSize = true;

    public static boolean checkVR() {
        NonVRMixinConfig.classLoad();
        return isVR;
    }
}
