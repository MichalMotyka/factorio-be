package com.example.factorio.translator;

import com.example.factorio.model.User;
import com.example.factorio.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class UserToUserDTO {


    public UserDTO toUserDTO(User user){
        return UserToDTO(user);
    }


    @Mappings({
            @Mapping(source = "login",target = "name"),
    })
    protected abstract UserDTO UserToDTO(User user);
}
