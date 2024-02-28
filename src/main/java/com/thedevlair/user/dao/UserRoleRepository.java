package com.thedevlair.user.dao;

import com.thedevlair.user.model.thirdparty.UserRoleDTO;
import com.thedevlair.user.model.thirdparty.UserRoleId;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRoleRepository extends R2dbcRepository<UserRoleDTO, UserRoleId> {

    @Transactional
    void deleteByUser_Id(Long id);
}
