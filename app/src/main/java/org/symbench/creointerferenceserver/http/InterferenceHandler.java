package org.symbench.creointerferenceserver.http;

import org.symbench.creointerferenceserver.creo.InterferenceAnalyzer;

import java.util.Hashtable;
import java.util.List;

public class InterferenceHandler {
    public static final String COMMAND = "interference";

    public static final String GLOBAL_INTERFERENCE = "global_interference";

    public static final String ASSEMBLY_PATH = "assembly_path";

    private InterferenceAnalyzer interferenceAnalyzer;

    public InterferenceHandler(InterferenceAnalyzer analyzer) {
        this.interferenceAnalyzer = analyzer;
    }


    public Hashtable<String, Object> handleFunction(String function, Hashtable<String, Object> input) throws Exception{
        if (function == null) {
            return null;
        }

        if(function.equals(GLOBAL_INTERFERENCE)) {
            computeGlobalInterference(input);
        } else {
            throw new UnsupportedOperationException("Function " + function + " is not supported");
        }
    }

    private List<Hashtable<String, Object>> computeGlobalInterference(Hashtable<String, Object> input) throws Exception {
        String  assemblyPath = (String) input.get(ASSEMBLY_PATH);
        if (assemblyPath == null) {
            return this.interferenceAnalyzer.getGlobalInterferences();
        } else {
            return this.interferenceAnalyzer.getGlobalInterferences(assemblyPath);
        }
    }
}
