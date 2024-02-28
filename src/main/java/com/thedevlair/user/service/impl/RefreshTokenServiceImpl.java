package com.thedevlair.user.service.impl;


import com.thedevlair.user.dao.RefreshTokenRepository;
import com.thedevlair.user.dao.UserRepository;
import com.thedevlair.user.exception.RefreshTokenException;
import com.thedevlair.user.exception.type.InternalServerErrorException;
import com.thedevlair.user.exception.type.NoContentFoundException;
import com.thedevlair.user.mapper.RefreshTokenMapper;
import com.thedevlair.user.model.business.RefreshToken;
import com.thedevlair.user.model.business.rs.RefreshTokenRs;
import com.thedevlair.user.model.thirdparty.RefreshTokenDTO;
import com.thedevlair.user.model.thirdparty.UserDTO;
import com.thedevlair.user.security.jwt.JwtUtils;
import com.thedevlair.user.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${thedevlair.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private final RefreshTokenMapper refreshTokenMapper;

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;



    @Override
    public Mono<RefreshToken> findByToken(String token) {
        return refreshTokenMapper.refreshTokenDTOToRefreshToken(refreshTokenRepository.findByToken(token));
    }

    @Override
    public Mono<RefreshToken> createRefreshToken(Long userId) {
        RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO();

        Optional<UserDTO> userDTO = userRepository.findById(userId);

        if (userDTO.isEmpty()) {
            throw new NoContentFoundException("Not found user.");
        }

        refreshTokenDTO.setUser(userDTO.get());
        refreshTokenDTO.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshTokenDTO.setToken(UUID.randomUUID().toString());

        try {
            return refreshTokenMapper.refreshTokenDTOToRefreshToken(refreshTokenRepository.save(refreshTokenDTO));
        } catch (Exception ex) {
            throw new InternalServerErrorException("Error processing request.");
        }

    }

    @Override
    public Mono<RefreshToken> verifyExpiration(RefreshToken refreshToken) {

        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.deleteByToken(refreshToken.getToken());
            throw new RefreshTokenException(refreshToken.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }

        return refreshToken;
    }

    @Transactional
    public void deleteByUserId(Long userId) {

        try {
            Optional<UserDTO> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                throw new NoContentFoundException("Not found user.");
            }

            refreshTokenRepository.deleteByUser(optionalUser.get());
        } catch (Exception ex) {
            throw new InternalServerErrorException("Error processing request.");
        }

    }

    @Override
    public Mono<ResponseEntity<RefreshTokenRs>> refreshToken(RefreshToken refreshToken) {
        String rfToken = refreshToken.getRefreshToken();

        return Optional.of(refreshTokenRepository.findByToken(rfToken))
                .map(this::validateExpiration
                ).map(RefreshTokenDTO::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new RefreshTokenRs(token, rfToken));
                })
                .orElseThrow(() -> new RefreshTokenException(rfToken,
                        "Refresh token is not in database!"));

    }

    private RefreshTokenDTO validateExpiration(RefreshTokenDTO refreshTokenDTO) {
        if (refreshTokenDTO.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshTokenDTO);
            throw new RefreshTokenException(refreshTokenDTO.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return refreshTokenDTO;
    }
}
