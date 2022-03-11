package org.symbench.creointerferenceserver.utils;

import java.util.Objects;

public class LogPropertiesLoader {
    static {
        String path = Objects.requireNonNull(LogPropertiesLoader.class.getClassLoader().getResource("logging.properties")).getFile();
        System.setProperty("java.util.logging.config.file", path);
    }
}
