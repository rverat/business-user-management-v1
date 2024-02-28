package com.thedevlair.user.model.business.rs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRs {
    private final String token;
    private final String refreshToken;
    private final String type = "Bearer";

    public RefreshTokenRs(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
