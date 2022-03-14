package org.symbench.creointerferenceserver.http;


import java.util.Hashtable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest {
    @JsonInclude(Include.NON_NULL)
    private String command;

    @JsonInclude(Include.NON_NULL)
    private String function;

    @JsonInclude(Include.NON_EMPTY)
    private Hashtable<String, Object> data;

    public Object put(String key, Object value) {
        if (data == null) {
            data = new Hashtable<String, Object>();
        }
        return data.put(key, value);
    }

    public Object get(String key) {
        if (data == null) {
            return null;
        }
        return data.get(key);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Hashtable<String, Object> getData() {
        return data;
    }

    public void setData(Hashtable<String, Object> data) {
        this.data = data;
    }
}