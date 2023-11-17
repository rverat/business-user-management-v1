package com.thedevlair.user.model.thirdparty;

import com.thedevlair.user.model.business.ERole;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ERole name;

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}