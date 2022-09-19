package org.vivecraft.extensions;

import com.mojang.math.Quaternion;
import org.vivecraft.client.render.VRArmRenderer;

import java.util.Map;

public interface EntityRenderDispatcherExtension {

    Quaternion getCameraOrientationOffset(float offset);

    Map<String, VRArmRenderer>  getArmSkinMap();
}
