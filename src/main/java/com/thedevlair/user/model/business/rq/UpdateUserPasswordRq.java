package com.thedevlair.user.model.business.rq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordRq(@NotBlank
                                   @Size(min = 8, max = 16)
                                   String oldPassword, @NotBlank
                                   @Size(min = 8, max = 16)
                                   String newPassword) {

}

