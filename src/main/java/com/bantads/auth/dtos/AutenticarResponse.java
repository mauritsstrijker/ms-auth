package com.bantads.auth.dtos;

public class AutenticarResponse {
    private String token;

    public AutenticarResponse() {
    }

    public AutenticarResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
