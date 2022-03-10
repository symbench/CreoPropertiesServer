package org.symbench.creointerferenceserver.creo;
import com.ptc.pfc.pfcSession.*;
import com.ptc.pfc.pfcAsyncConnection.*;
import com.ptc.cipjava.*;

public class CreoSession {
    public static Session acquire() throws jxthrowable {
        System.loadLibrary ("pfcasyncmt");
        AsyncConnection ac = pfcAsyncConnection.AsyncConnection_Connect (null, null, null, 10);
        return ac.GetSession();
    };
}
