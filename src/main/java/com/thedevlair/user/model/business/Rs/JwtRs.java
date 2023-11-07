package com.thedevlair.user.model.business.Rs;

import java.util.List;

public class JwtRs {
    private Long id;
    private String username;
    private boolean isEnabled;
    private List<String> roles;
    private String token;
    private String type = "Bearer";
    private String refreshToken;

    public JwtRs(Long id, String username, boolean isEnabled, List<String> roles,
                 String token, String type, String refreshToken) {
        this.id = id;
        this.username = username;
        this.isEnabled = isEnabled;
        this.roles = roles;
        this.token = token;
        this.type = type;
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
