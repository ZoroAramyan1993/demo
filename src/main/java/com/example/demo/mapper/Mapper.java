package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public User toUser(UserDto userDto){
        return new User();
    }
}
