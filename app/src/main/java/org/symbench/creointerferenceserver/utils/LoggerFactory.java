package org.symbench.creointerferenceserver.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerFactory {
    static {
        try (InputStream is = LoggerFactory.class.getClassLoader().
                getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger(String className) {
        return Logger.getLogger(className);
    }
}
