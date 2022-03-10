package org.symbench.creointerferenceserver.http;

public class InterferenceRequest {
    private String assemblyPath;

    public InterferenceRequest(String assemblyPath) {
        this.assemblyPath = assemblyPath;
    }

    public String getAssemblyPath() {
        return assemblyPath;
    }

    public void setAssemblyPath(String assemblyPath) {
        this.assemblyPath = assemblyPath;
    }
}
