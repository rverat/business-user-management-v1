package com.thedevlair.user.dao;

import com.thedevlair.user.model.thirdparty.RefreshTokenDTO;
import com.thedevlair.user.model.thirdparty.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenDTO, Long> {
    RefreshTokenDTO findByToken(String token);

    void deleteByToken(String token);

    @Modifying
    int deleteByUser(UserDTO user);
}
