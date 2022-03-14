package org.symbench.creointerferenceserver.creo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ptc.cipjava.*;

import com.ptc.pfc.pfcAssembly.*;
import com.ptc.pfc.pfcInterference.*;
import com.ptc.pfc.pfcSelect.*;
import com.ptc.pfc.pfcModel.*;
import com.ptc.pfc.pfcSession.*;

import org.symbench.creointerferenceserver.utils.LoggerFactory;

public class InterferenceAnalyzer {

    public static final String PART_1_NAME = "part_1_name";
    public static final String PART_2_NAME = "part_2_name";
    public static final String INTERFERENCE_VOLUME = "interference_volume";
    public static final String NUMBER_OF_INTERFERENCES = "num_interferences";
    public static final String INTERFERENCES = "interferences";

    private static final Logger logger = LoggerFactory.getLogger(InterferenceAnalyzer.class.getName());

    public Hashtable<String, Object> getGlobalInterferences(String assemblyPath) throws jxthrowable {
        Assembly assembly = this.loadAssemblyIfNeeded(assemblyPath);
        return computeGlobalInterferences(assembly);
    }

    public Hashtable<String, Object> getGlobalInterferences() throws jxthrowable {
        Assembly assembly = this.loadAssemblyIfNeeded(null);
        return computeGlobalInterferences(assembly);
    }

    private static Hashtable<String, Object> computeGlobalInterferences(Assembly assembly) throws jxthrowable {
        GlobalEvaluator globalEvaluator = pfcInterference.CreateGlobalEvaluator(assembly);
        GlobalInterferences globalInterferences = globalEvaluator.ComputeGlobalInterference(true);
        Hashtable<String, Object> output = new Hashtable<>();
        ArrayList<Hashtable<String, Object>> interferences = new ArrayList<>();


        if (globalInterferences == null) {
            output.put(NUMBER_OF_INTERFERENCES, 0);
        } else {
            // ToDo: Add Log Message
            int size = globalInterferences.getarraysize();
            output.put(NUMBER_OF_INTERFERENCES, size);
            for(int j = 0; j < size; j++) {
                GlobalInterference interference = globalInterferences.get(j);
                SelectionPair interferingPairs = interference.GetSelParts();
                Hashtable<String, Object> detail = new Hashtable<>();
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

    private Assembly loadAssemblyIfNeeded(String assemblyPath) throws jxthrowable {
        Session session = CreoSession.acquire();
        if(assemblyPath != null) {
            try {
                Path ap = Paths.get(assemblyPath);
                String fileName = ap.getFileName().toString();
                String directory = ap.getParent().toString();
                session.ChangeDirectory(directory);

                logger.info("Changed the working directory to " + directory);

                ModelDescriptor modelDescriptor= pfcModel.ModelDescriptor_CreateFromFileName(fileName);
                Model model = session.RetrieveModel(modelDescriptor);
                model.Display(); //ToDo: What to do with this when no GUI?

                logger.log(Level.INFO, "Assmebly Loaded: " + assemblyPath);
                return (Assembly) model;
            }
            catch(jxthrowable x) {
                logger.log(Level.SEVERE, "Cannot load assembly at " + assemblyPath);
                throw x;
            }
        } else {
            return (Assembly) session.GetActiveModel();
        }
    }

    public static InterferenceAnalyzer getInstance() {
        return new InterferenceAnalyzer();
    }
}
