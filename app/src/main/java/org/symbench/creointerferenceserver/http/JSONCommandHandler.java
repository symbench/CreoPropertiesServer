package org.symbench.creointerferenceserver.http;

import java.util.Map;

public abstract class JSONCommandHandler {
    public abstract Map<String, Object> handleFunction(String function, Map<String, Object> data) throws Exception;
}
