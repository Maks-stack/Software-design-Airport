package com.emse.airportSystem;

import java.util.HashMap;
import java.util.Map;

public class BackendErrorResponse{
    Map<String, String> error = new HashMap<String, String>();

    public BackendErrorResponse(Exception e){
        this.error.put("message", e.getMessage());
        this.error.put("class", e.getClass().getName());
    }

    public Map<String, String> getError() {
        return error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }
}