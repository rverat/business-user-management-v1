package com.thedevlair.user.model.business;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Role {
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
