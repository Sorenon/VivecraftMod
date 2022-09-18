package org.vivecraft;

import java.nio.file.Path;

public interface IPlatformService {

    boolean isModLoaded(String name);

    Path getConfigPath(String fileName);

    boolean isDedicatedServer();

    String getModloader();

    String getModVersion();
}
