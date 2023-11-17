package com.thedevlair.user.dao;

import com.thedevlair.user.model.thirdparty.UserRoleDTO;
import com.thedevlair.user.model.thirdparty.UserRoleId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleDTO, UserRoleId> {

    @Transactional
    void deleteByUser_Id(Long id);
}
