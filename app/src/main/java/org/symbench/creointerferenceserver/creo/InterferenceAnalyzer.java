package org.symbench.creointerferenceserver.creo;

import java.util.ArrayList;

import com.ptc.pfc.pfcSession.*;
import com.ptc.cipjava.*;

import com.ptc.pfc.pfcAssembly.*;
import com.ptc.pfc.pfcInterference.*;
import com.ptc.pfc.pfcSelect.*;
import com.ptc.pfc.pfcModel.*;

import org.symbench.creointerferenceserver.http.InterferenceDetails;

public class InterferenceAnalyzer{
    private Session session;
    public InterferenceAnalyzer() {
        try {
            this.session = CreoSession.acquire();
        } catch (Exception x) {
            System.out.println(x.toString());
        }
    }

    public ArrayList<InterferenceDetails> getInterferences(String assemblyPath) throws jxthrowable {
        Assembly assembly = this.loadAssemblyIfNeeded(assemblyPath);
        return this.computeInterferences(assembly);
    }

    private ArrayList<InterferenceDetails> computeInterferences(Assembly assembly) throws jxthrowable {
        GlobalEvaluator globalEvaluator = pfcInterference.CreateGlobalEvaluator(assembly);
        GlobalInterferences globalInterferences = globalEvaluator.ComputeGlobalInterference(true);

        if (globalInterferences == null) {
            return new ArrayList<>();
        } else {
            ArrayList<InterferenceDetails> interferences = new ArrayList<InterferenceDetails>();
            // ToDo: Add Log Message
            int size = globalInterferences.getarraysize();
            for(int j = 0; j < size; j++) {
                System.out.println(j);
                GlobalInterference interference = globalInterferences.get(j);
                SelectionPair interferingPairs = interference.GetSelParts();
                InterferenceDetails detail = new InterferenceDetails(
                        interferingPairs.GetSel1().GetSelModel().GetFullName(),
                        interferingPairs.GetSel2().GetSelModel().GetFullName(),
                        interference.GetVolume().ComputeVolume()
                );
                interferences.add(detail);
            }
            return interferences;
        }
    }

    private Assembly loadAssemblyIfNeeded(String assemblyPath) throws jxthrowable {
        if(assemblyPath != null) {
            ModelType modelType = ModelType.MDL_ASSEMBLY;
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
