package org.symbench.creointerferenceserver.creo;

import java.util.ArrayList;
import java.util.Hashtable;

import com.ptc.cipjava.*;

import com.ptc.pfc.pfcAssembly.*;
import com.ptc.pfc.pfcInterference.*;
import com.ptc.pfc.pfcSelect.*;
import com.ptc.pfc.pfcModel.*;

public class InterferenceAnalyzer {

    public static final String PART_1_NAME = "part_1_name";
    public static final String PART_2_NAME = "part_2_name";
    public static final String INTERFERENCE_VOLUME = "interference_volume";

    public ArrayList<Hashtable<String, Object>> getGlobalInterferences(String assemblyPath) throws jxthrowable {
        Assembly assembly = this.loadAssemblyIfNeeded(assemblyPath);
        return this.computeGlobalInterferences(assembly);
    }

    public ArrayList<Hashtable<String, Object>> getGlobalInterferences() throws jxthrowable {
        Assembly assembly = this.loadAssemblyIfNeeded(null);
        return this.computeGlobalInterferences(assembly);
    }

    private static ArrayList<Hashtable<String, Object>> computeGlobalInterferences(Assembly assembly) throws jxthrowable {
        GlobalEvaluator globalEvaluator = pfcInterference.CreateGlobalEvaluator(assembly);
        GlobalInterferences globalInterferences = globalEvaluator.ComputeGlobalInterference(true);

        if (globalInterferences == null) {
            return new ArrayList<>();
        } else {
            ArrayList<Hashtable<String, Object>> interferences = new ArrayList<>();
            // ToDo: Add Log Message
            int size = globalInterferences.getarraysize();
            for(int j = 0; j < size; j++) {
                System.out.println(j);
                GlobalInterference interference = globalInterferences.get(j);
                SelectionPair interferingPairs = interference.GetSelParts();
                Hashtable<String, Object> detail = new Hashtable<>();
                detail.put(PART_1_NAME, interferingPairs.GetSel1().GetSelModel().GetFullName());
                detail.put(PART_2_NAME, interferingPairs.GetSel2().GetSelModel().GetFullName());
                detail.put(INTERFERENCE_VOLUME, interference.GetVolume().ComputeVolume());
                interferences.add(detail);
            }
            return interferences;
        }
    }

    private Assembly loadAssemblyIfNeeded(String assemblyPath) throws jxthrowable {
        if(assemblyPath != null) {
            ModelType modelType = ModelType.MDL_ASSEMBLY;
            const session = CreoSession.acquire();
            try {
                ModelDescriptor modelDescriptor= pfcModel.ModelDescriptor_Create(modelType, assemblyPath, "");
                Model model = this.session.RetrieveModel(modelDescriptor);
                model.Display();
                System.out.println("Assmebly Loaded: " + assemblyPath);
                return (Assembly) model;
            }
            catch(jxthrowable x) {
                System.out.println("Cannot load assembly: " + x);
                throw x;
            }
        } else {
            return (Assembly) this.session.GetActiveModel();
        }
    }
}
