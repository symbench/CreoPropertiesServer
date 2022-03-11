package org.symbench.creointerferenceserver.http;

import java.util.Hashtable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {
    private Hashtable<String, Object> data;

    public Hashtable<String, Object> getData() {
        return data;
    }

    public void setData(Hashtable<String, Object> data) {
        this.data = data;
    }
}
