package com.thedevlair.user.model.thirdparty;

import jakarta.persistence.*;


@Entity
@Table(name = "user_roles")
public class UserRoleDTO {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserDTO user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleDTO role;
}