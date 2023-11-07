package com.thedevlair.user.mapper;

import com.thedevlair.user.model.business.RefreshToken;
import com.thedevlair.user.model.thirdparty.RefreshTokenDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshToken refreshTokenDTOToRefreshToken(RefreshTokenDTO refreshTokenDTO);
    RefreshTokenDTO refreshTokenToRefreshTokenDTO(RefreshToken refreshToken);
}
