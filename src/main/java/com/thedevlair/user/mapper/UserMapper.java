package com.thedevlair.user.mapper;

import com.thedevlair.user.model.business.User;
import com.thedevlair.user.model.thirdparty.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDTOToUser(UserDTO userDTO);

    UserDTO userToUserDTO(User user);

}
