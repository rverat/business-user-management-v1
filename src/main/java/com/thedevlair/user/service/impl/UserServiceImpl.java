package com.thedevlair.user.service.impl;

import com.thedevlair.user.dao.RoleRepository;
import com.thedevlair.user.dao.UserRepository;
import com.thedevlair.user.exception.type.ConflictDataException;
import com.thedevlair.user.exception.type.InternalServerErrorException;
import com.thedevlair.user.exception.type.NoContentFoundException;
import com.thedevlair.user.mapper.UserMapper;
import com.thedevlair.user.model.business.ERole;
import com.thedevlair.user.model.business.RefreshToken;
import com.thedevlair.user.model.business.Rq.LoginRequest;
import com.thedevlair.user.model.business.Rq.UpdateUserPasswordRq;
import com.thedevlair.user.model.business.Rs.JwtRs;
import com.thedevlair.user.model.business.Rs.MessageRs;
import com.thedevlair.user.model.business.User;
import com.thedevlair.user.model.thirdparty.RoleDTO;
import com.thedevlair.user.model.thirdparty.UserDTO;
import com.thedevlair.user.security.jwt.JwtUtils;
import com.thedevlair.user.security.service.UserDetailsImpl;
import com.thedevlair.user.service.RefreshTokenService;
import com.thedevlair.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           AuthenticationManager authenticationManager,
                           RoleRepository roleRepository, PasswordEncoder encoder,
                           JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public ResponseEntity<JwtRs> authenticate(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtRs(userDetails.getId(),
                userDetails.getUsername(),
                userDetails.isEnabled(),
                roles,
                jwt,
                "Bearer",
                refreshToken.getToken()));
    }

    @Override
    public ResponseEntity<MessageRs> sessionValidate(String authorizationHeader) {
        try {
            // Extract the token from the Authorization header
            String token = authorizationHeader.substring("Bearer ".length());

            // Validate the token using the JwtUtils class
            boolean isValidToken = jwtUtils.validateJwtToken(token);

            if (isValidToken) {
                return ResponseEntity.ok(new MessageRs("Token is valid."));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageRs("Invalid token."));
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Error processing request.");
        }
    }

    @Override
    public ResponseEntity<User> getUser() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Optional<UserDTO> optionalUser = userRepository.findById(userDetails.getId());
            if (optionalUser.isEmpty()) {
                throw new NoContentFoundException("Not found user.");
            }

            return ResponseEntity.ok(userMapper.userDTOToUser(optionalUser.get()));

        } catch (Exception e) {
            throw new InternalServerErrorException("Error processing request.");
        }
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        try {
            Optional<UserDTO> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                throw new NoContentFoundException("Not found user.");
            }

            return ResponseEntity.ok(userMapper.userDTOToUser(optionalUser.get()));

        } catch (Exception e) {
            throw new InternalServerErrorException("Error processing request.");
        }
    }

    @Override
    public ResponseEntity<MessageRs> create(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ConflictDataException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictDataException("Error: Email is already in use!");
        }

        UserDTO userDTO = userMapper.userToUserDTO(user);

        userDTO.setRoles(getUserRoles(user));
        userDTO.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(userDTO);

        return ResponseEntity.ok(new MessageRs("User registered successfully!"));

    }

    @Override
    public ResponseEntity<MessageRs> update(User user) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       if(!userDetails.getUsername().equals(user.getUsername())){
           if (userRepository.existsByUsername(user.getUsername())) {
               throw new ConflictDataException("Error: Username is already taken!");
           }
       }

        if(!userDetails.getEmail().equals(user.getEmail())){
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new ConflictDataException("Error: Email is already in use!");
            }
        }

        UserDTO userDTO = userMapper.userToUserDTO(user);

        userDTO.setRoles(getUserRoles(user));
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        userRepository.save(userDTO);

        return ResponseEntity.ok(new MessageRs("User registered successfully!"));

    }

    private Set<RoleDTO> getUserRoles(User user) {
        return Optional.ofNullable(user.getRoles())
                .map(roles -> roles.stream()
                        .map(role -> switch (role.getName().toString()) {
                            case "admin" -> roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            case "mod" -> roleRepository.findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            default -> roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        }).collect(Collectors.toSet()))
                .orElse(Set.of(roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."))));
    }

    @Override
    public ResponseEntity<MessageRs> updatePassword(UpdateUserPasswordRq passwordRq) {
        // Retrieve the currently authenticated user's details
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the user exists
        Optional<UserDTO> optionalUser = userRepository.findById(userDetails.getId());
        if (optionalUser.isEmpty()) {
            throw new NoContentFoundException("Not found user.");
        }

        UserDTO user = optionalUser.get();

        user.setPassword(encoder.encode(passwordRq.password()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageRs("User updated successfully!"));
    }

    @Override
    public ResponseEntity<MessageRs> logout(String token) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        refreshTokenService.deleteByUserId(userId);
        JwtUtils.invalidateToken(token);
        return ResponseEntity.ok(new MessageRs("Log out successful!"));
    }

    @Override
    public ResponseEntity<MessageRs> delete(Long id) {
        try {
            userRepository.deleteById(id);
        }catch (Exception ex){
            throw new InternalServerErrorException("Error processing request.");
        }
        return ResponseEntity.ok(new MessageRs("Log out successful!"));
    }
}