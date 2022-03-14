package org.symbench.creointerferenceserver.http;

import java.util.Hashtable;

public abstract class JSONCommandHandler {
    public abstract Hashtable<String, Object> handleFunction(String function, Hashtable<String, Object> data) throws Exception;
}
