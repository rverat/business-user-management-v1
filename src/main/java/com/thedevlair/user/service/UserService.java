package com.thedevlair.user.service;

import com.thedevlair.user.model.business.rq.LoginRequest;
import com.thedevlair.user.model.business.rq.UpdateUserPasswordRq;
import com.thedevlair.user.model.business.rs.JwtRs;
import com.thedevlair.user.model.business.rs.MessageRs;
import com.thedevlair.user.model.business.rs.UserRs;
import com.thedevlair.user.model.business.User;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<ResponseEntity<JwtRs>> authenticate(LoginRequest loginRequest);

    Mono<ResponseEntity<MessageRs>> sessionValidate(String authorizationHeader);

    Mono<ResponseEntity<UserRs>> getUser();

    Mono<ResponseEntity<UserRs>> getUserById(Long id);

    Mono<ResponseEntity<MessageRs>> create(User user);

    Mono<ResponseEntity<MessageRs>> update(User user);

    Mono<ResponseEntity<MessageRs>> updatePassword(String token, UpdateUserPasswordRq passwordRq);

    Mono<ResponseEntity<MessageRs>> logout(String token);

    Mono<ResponseEntity<MessageRs>> delete(Long id);

}
