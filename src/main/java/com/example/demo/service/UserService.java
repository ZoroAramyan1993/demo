package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import org.springframework.http.ResponseEntity;



import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> get(Integer id);

    Optional<User> save(User user);

    Optional<User> getByEmail(String emil);

    boolean existsByEmail(String email);

    Optional<User> updateUser(UserDto userDto, Integer id);

    List<User> getAll();

    void deleteById(Integer id);
}
