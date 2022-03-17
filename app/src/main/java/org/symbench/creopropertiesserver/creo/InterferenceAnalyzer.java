package org.symbench.creopropertiesserver.creo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ptc.cipjava.*;

import com.ptc.pfc.pfcAssembly.*;
import com.ptc.pfc.pfcInterference.*;
import com.ptc.pfc.pfcSelect.*;

import org.symbench.creopropertiesserver.utils.LoggerFactory;

public class InterferenceAnalyzer extends BaseAnalyzer{

    public static final String PART_1_NAME = "part_1_name";
    public static final String PART_2_NAME = "part_2_name";
    public static final String INTERFERENCE_VOLUME = "interference_volume";
    public static final String NUMBER_OF_INTERFERENCES = "num_interferences";
    public static final String INTERFERENCES = "interferences";

    private static final Logger logger = LoggerFactory.getLogger(InterferenceAnalyzer.class.getName());

    public Map<String, Object> getGlobalInterferences(String assemblyPath) throws jxthrowable {
        Assembly assembly = this.loadAssemblyIfNeeded(assemblyPath);
        return computeGlobalInterferences(assembly);
    }

    public Map<String, Object> getGlobalInterferences() throws jxthrowable {
        Assembly assembly = this.loadAssemblyIfNeeded(null);
        return computeGlobalInterferences(assembly);
    }

    private static Map<String, Object> computeGlobalInterferences(Assembly assembly) throws jxthrowable {
        GlobalEvaluator globalEvaluator = pfcInterference.CreateGlobalEvaluator(assembly);
        GlobalInterferences globalInterferences = globalEvaluator.ComputeGlobalInterference(true);
        Map<String, Object> output = new HashMap<>();
        ArrayList<HashMap<String, Object>> interferences = new ArrayList<>();


        if (globalInterferences == null) {
            output.put(NUMBER_OF_INTERFERENCES, 0);
        } else {
            // ToDo: Add Log Message
            int size = globalInterferences.getarraysize();
            output.put(NUMBER_OF_INTERFERENCES, size);
            for(int j = 0; j < size; j++) {
                GlobalInterference interference = globalInterferences.get(j);
                SelectionPair interferingPairs = interference.GetSelParts();
                HashMap<String, Object> detail = new HashMap<>();
                detail.put(PART_1_NAME, interferingPairs.GetSel1().GetSelModel().GetFullName());
                detail.put(PART_2_NAME, interferingPairs.GetSel2().GetSelModel().GetFullName());
                detail.put(INTERFERENCE_VOLUME, interference.GetVolume().ComputeVolume());
                logger.log(Level.FINE,"Interference + " + j + ": " + detail.toString());
                interferences.add(detail);
            }
        }
        output.put(INTERFERENCES, interferences);
        return output;
    }

    public static InterferenceAnalyzer getInstance() {
        return new InterferenceAnalyzer();
    }
}
