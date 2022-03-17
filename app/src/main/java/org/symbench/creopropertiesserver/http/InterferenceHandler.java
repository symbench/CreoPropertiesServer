package org.symbench.creopropertiesserver.http;

import org.symbench.creopropertiesserver.creo.InterferenceAnalyzer;

import java.util.Map;
import java.util.logging.Logger;

public class InterferenceHandler extends JSONCommandHandler {
    public static final String COMMAND = "interference";

    public static final String GLOBAL_INTERFERENCE = "global_interference";

    public static final String MODEL_PATH = "model_path";

    private static final Logger logger = Logger.getLogger(InterferenceHandler.class.getName());

    private InterferenceAnalyzer interferenceAnalyzer;

    public InterferenceHandler(InterferenceAnalyzer analyzer) {
        this.interferenceAnalyzer = analyzer;
    }


    public Map<String, Object> handleFunction(String function, Map<String, Object> input) throws Exception {
        logger.info("Function " + function + " called with data "  + input);
        if(function.equals(GLOBAL_INTERFERENCE)) {
            return computeGlobalInterference(input);
        } else {
            throw new UnsupportedOperationException("Function " + function + " is not supported");
        }
    }

    private Map<String, Object> computeGlobalInterference(Map<String, Object> input) throws Exception {
        if(input == null) {
            return this.interferenceAnalyzer.getGlobalInterferences();
        }
        String  assemblyPath = (String) input.get(MODEL_PATH);
        if (assemblyPath == null) {
            return this.interferenceAnalyzer.getGlobalInterferences();
        } else {
            return this.interferenceAnalyzer.getGlobalInterferences(assemblyPath);
        }
    }
}
