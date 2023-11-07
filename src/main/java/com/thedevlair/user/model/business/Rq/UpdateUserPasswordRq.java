package com.thedevlair.user.model.business.Rq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordRq(@NotBlank
                                   @Size(min = 8, max = 16)
                                   String password) {

}

