package com.thedevlair.user.model.business.Rs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRs {
    private String token;
    private String refreshToken;
    private String type = "Bearer";

    public RefreshTokenRs(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
