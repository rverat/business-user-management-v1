package com.thedevlair.user.mapper;

import com.thedevlair.user.model.business.Rs.UserRs;
import com.thedevlair.user.model.business.User;
import com.thedevlair.user.model.thirdparty.UserDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDTOToUser(UserDTO userDTO);
    UserRs userDTOToUserRs(UserDTO userDTO);
    UserDTO userToUserDTO(User user);

}
