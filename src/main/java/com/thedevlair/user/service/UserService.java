package com.thedevlair.user.service;

import com.thedevlair.user.model.business.Rq.LoginRequest;
import com.thedevlair.user.model.business.Rq.UpdateUserPasswordRq;
import com.thedevlair.user.model.business.Rs.JwtRs;
import com.thedevlair.user.model.business.Rs.MessageRs;
import com.thedevlair.user.model.business.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    ResponseEntity<JwtRs> authenticate(LoginRequest loginRequest);

    ResponseEntity<MessageRs> sessionValidate(String authorizationHeader);

    ResponseEntity<User> getUser();

    ResponseEntity<User> getUserById(Long id);

    ResponseEntity<MessageRs> create(User user);

    ResponseEntity<MessageRs> update(User user);

    ResponseEntity<MessageRs> updatePassword(UpdateUserPasswordRq passwordRq);
    ResponseEntity<MessageRs> logout(String token);
    ResponseEntity<MessageRs> delete(Long id);

}
