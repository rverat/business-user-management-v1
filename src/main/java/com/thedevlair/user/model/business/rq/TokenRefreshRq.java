package com.thedevlair.user.model.business.rq;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRefreshRq {
    @NotBlank
    private String refreshToken;
}