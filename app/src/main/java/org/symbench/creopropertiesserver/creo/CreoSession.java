package org.symbench.creopropertiesserver.creo;
import com.ptc.pfc.pfcSession.*;
import com.ptc.pfc.pfcAsyncConnection.*;
import com.ptc.cipjava.*;
import org.symbench.creopropertiesserver.utils.LoggerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CreoSession {
    private static AsyncConnection ac;

    private static final Logger logger = LoggerFactory.getLogger(CreoSession.class.getName());

    public static void connect() {
        try {
            System.loadLibrary ("pfcasyncmt");
            ac = pfcAsyncConnection.AsyncConnection_Connect (null, null, null, 10);
        } catch (Exception e) {
            String message = "Failed to acquire a CREO session";
            throw new SessionAcquisitionException(message);
        }
    }

    public static Session acquire() throws jxthrowable{
        if(ac == null) {
            connect();
        }
        logger.log(Level.FINEST, "About to acquire a session");
        Session session = ac.GetSession();
        logger.log(Level.INFO, "Successfully acquired a PROToolkit Session");
        return session;
    }
}
