package com.vmturbo.tetration.simulator.service;

public class NetworkFlow {
    private String offset;

    private Flow[] results;

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public Flow[] getResults() {
        if (results == null) {
            return new Flow[]{};
        }
        return results;
    }

    public void setResults(Flow[] results) {
        this.results = results;
    }
}
