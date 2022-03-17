package org.symbench.creopropertiesserver.http;

import org.symbench.creopropertiesserver.creo.MassPropertiesAnalyzer;

import java.util.Map;
import java.util.logging.Logger;

public class MassPropertiesHandler extends JSONCommandHandler{
    public static final String COMMAND = "file";

    public static final String FILE_MASSPROPS = "massproperties";

    public static final String ASSEMBLY_PATH = "assembly_path";

    private static final Logger logger = Logger.getLogger(InterferenceHandler.class.getName());

    private MassPropertiesAnalyzer massPropertiesAnalyzer;

    public MassPropertiesHandler(MassPropertiesAnalyzer analyzer) {
        this.massPropertiesAnalyzer = analyzer;
    }

    @Override
    public Map<String, Object> handleFunction(String function, Map<String, Object> input) throws Exception {
        if (function.equals(FILE_MASSPROPS)) {
            return computeMassProperties(input);
        } else {
            throw new UnsupportedOperationException("Function " + function + " is not supported");
        }
    }


    private Map<String, Object> computeMassProperties(Map<String, Object> input) throws Exception {
        if(input == null) {
            return this.massPropertiesAnalyzer.getFileMassProperties();
        } else {
            String assemblyPath = (String) input.get(ASSEMBLY_PATH);
            if(assemblyPath == null) {
                return this.massPropertiesAnalyzer.getFileMassProperties();
            } else {
                return this.massPropertiesAnalyzer.getFileMassProperties(assemblyPath);
            }
        }
    }
}
