package com.thedevlair.expose.web;

import com.thedevlair.user.model.business.RefreshToken;
import com.thedevlair.user.model.business.Rq.LoginRequest;
import com.thedevlair.user.model.business.Rq.UpdateUserPasswordRq;
import com.thedevlair.user.model.business.Rs.JwtRs;
import com.thedevlair.user.model.business.Rs.MessageRs;
import com.thedevlair.user.model.business.Rs.RefreshTokenRs;
import com.thedevlair.user.model.business.Rs.UserRs;
import com.thedevlair.user.model.business.User;
import com.thedevlair.user.service.RefreshTokenService;
import com.thedevlair.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    public UserController(UserService userService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signIn")
    @Operation(summary = "Authenticate user with username and password")
    public ResponseEntity<JwtRs> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        return userService.authenticate(loginRequest);

    }

    @GetMapping("/getUser")
    @Operation(summary = "Get user info logged in")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserRs> getUser() {

        return userService.getUser();
    }

    @GetMapping("/getUserById/{userId}")
    @Operation(summary = "Get user info logged in by userId")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserRs> getUserById(@PathVariable("userId") Long userId) {

        return userService.getUserById(userId);

    }

    @PostMapping("/sessionValidate")
    @Operation(summary = "Validate user session")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageRs> sessionValidate(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        return userService.sessionValidate(authorizationHeader);
    }


    @PostMapping("/signUp")
    @Operation(summary = "Create user")
    public ResponseEntity<MessageRs> registerUser(@Valid @RequestBody User user) {

        return userService.create(user);
    }

    @PatchMapping()
    @Operation(summary = "Update user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageRs> updateUser(@Valid @RequestBody User user) {

        return userService.update(user);

    }

    @PatchMapping("/updatePassword")
    @Operation(summary = "Update user password")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageRs> updateUserPass(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @Valid @RequestBody UpdateUserPasswordRq passwordRq) {

        return userService.updatePassword(authorizationHeader, passwordRq);

    }

    @PostMapping("/refreshToken")
    @Operation(summary = "Create access token from refresh token")
    public ResponseEntity<RefreshTokenRs> refreshToken(@Valid @RequestBody RefreshToken refreshToken) {

        return refreshTokenService.refreshToken(refreshToken);

    }

    @PostMapping("/signOut")
    @Operation(summary = " Logout user session")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageRs> logoutUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        return userService.logout(authorizationHeader);

    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by userId")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageRs> deleteUser(@PathVariable("userId") Long userId) {
        return userService.delete(userId);
    }
}
