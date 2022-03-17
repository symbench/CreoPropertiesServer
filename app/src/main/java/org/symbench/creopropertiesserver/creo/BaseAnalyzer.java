package org.symbench.creopropertiesserver.creo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ptc.cipjava.*;

import com.ptc.pfc.pfcModel.*;
import com.ptc.pfc.pfcSession.*;

import org.symbench.creopropertiesserver.utils.LoggerFactory;

public class BaseAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(BaseAnalyzer.class.getName());

    protected Model getModel(String modelPath) throws jxthrowable {
        Session session = CreoSession.acquire();
        if(modelPath != null) {
            try {
                System.out.println(modelPath);
                Path ap = Paths.get(modelPath);
                String fileName = ap.getFileName().toString();
                String directory = ap.getParent().toString();
                session.ChangeDirectory(directory);

                logger.info("Changed the working directory to " + directory);

                ModelDescriptor modelDescriptor= pfcModel.ModelDescriptor_CreateFromFileName(fileName);
                Model model = session.RetrieveModel(modelDescriptor);
                model.Display(); //ToDo: What to do with this when no GUI?

                logger.log(Level.INFO, "Model Loaded: " + modelPath);
                return model;
            }
            catch(jxthrowable x) {
                logger.log(Level.SEVERE, "Cannot load assembly at " + modelPath);
                throw x;
            }
        } else {
            return (Model) session.GetActiveModel();
        }
    }
}
