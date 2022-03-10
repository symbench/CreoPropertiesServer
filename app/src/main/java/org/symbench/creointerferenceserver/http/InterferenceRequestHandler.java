package org.symbench.creointerferenceserver.http;


import org.symbench.creointerferenceserver.creo.InterferenceAnalyzer;

import java.util.ArrayList;

public class InterferenceRequestHandler {
    private InterferenceAnalyzer interferenceAnalyzer;

    public InterferenceRequestHandler() {
        this.interferenceAnalyzer = new InterferenceAnalyzer();
    }

    public ArrayList<InterferenceDetails> handle(InterferenceRequest request) throws Exception{
        return this.interferenceAnalyzer.getInterferences(null);
    }
}
