package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import org.springframework.http.ResponseEntity;



import java.util.List;
import java.util.Optional;

public interface UserService {

    ResponseEntity<User> addUser(UserDto userDto);

    ResponseEntity<User> get(Integer id);

    ResponseEntity<User> getByEmail(String emil);

    boolean userExist(String email);

    public Optional<User> updateUser(UserDto userDto, Integer id);

    List<User> getAll();

    ResponseEntity delete(Integer id);
}
