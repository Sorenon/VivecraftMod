package org.vivecraft;

import java.util.ServiceLoader;

public class TMP {
    public static final IPlatformService SERVICE = ServiceLoader.load(IPlatformService.class).findFirst().get();
}
