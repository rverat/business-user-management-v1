package com.thedevlair.user.model.business.rs;

import com.thedevlair.user.model.business.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserRs {

    private Integer id;

    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain alphanumeric characters and underscores")
    private String username;

    @Size(max = 80)
    @Email
    private String email;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 125)
    private String profilePicture;

    private Date createdAt;

    private Date updatedAt;

    private boolean isEnabled;

    private Set<Role> roles = new HashSet<>();

}
