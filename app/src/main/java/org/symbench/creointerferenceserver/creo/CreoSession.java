package org.symbench.creointerferenceserver.creo;
import com.ptc.pfc.pfcSession.*;
import com.ptc.pfc.pfcAsyncConnection.*;
import com.ptc.cipjava.*;

public class CreoSession {
    private static AsyncConnection ac;
    public static void connect() throws jxthrowable {
        System.loadLibrary ("pfcasyncmt");
        ac = pfcAsyncConnection.AsyncConnection_Connect (null, null, null, 10);
    }

    public static Session acquire() throws jxthrowable{
        if(ac == null) {
            connect();
        }
        System.out.println("Acquiring session");
        return ac.GetSession();
    }
}
