package com.thedevlair.user.model.business.Rq;

import jakarta.validation.constraints.NotBlank;

public class TokenRefreshRq {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}