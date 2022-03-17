package org.symbench.creopropertiesserver.creo;

import org.symbench.creopropertiesserver.utils.LoggerFactory;

import java.util.Objects;
import java.util.logging.Logger;

public class CREOEnvironment {


    public static final String CREO_HOME = "CREO_HOME";

    public static final String CREO_COMMON = "CREO_COMMON";

    public static final String PATH = "Path";

    public static final String PRO_COMM_MSG_EXE = "PRO_COMM_MSG_EXE";

    public static final String CREO_LIB_REL_PATH = "\\Common Files\\x86e_win64\\lib";

    private static final Logger logger = LoggerFactory.getLogger(CREOEnvironment.class.getName());

    private static void assertNonNullEnvironmentVar(String title) {
        String value = System.getenv(title);
        if(Objects.isNull(value)) {
            throw new RuntimeException("Environment variable " + title + " is not set.");
        }
        logger.info( title + ": " + value);
    }

    private static void assetCREOLibinPath() {
        String path = System.getenv(PATH);
        if(!path.contains(System.getenv(CREO_HOME) + CREO_LIB_REL_PATH)) {
            throw new RuntimeException("CREO library is not in path");
        }
    }

    public static void verifyCREOEnvironment() {
        assertNonNullEnvironmentVar(CREO_HOME);
        assertNonNullEnvironmentVar(CREO_COMMON);
        assertNonNullEnvironmentVar(PRO_COMM_MSG_EXE);
        assertNonNullEnvironmentVar(PATH);
    }

}
