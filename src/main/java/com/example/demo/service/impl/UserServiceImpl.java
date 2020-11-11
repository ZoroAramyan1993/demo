package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public ResponseEntity<User> addUser(UserDto userDto) {
        User user = new User(userDto.getName(),userDto.getSurName(),userDto.getEmail(),userDto.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    public Optional<User> updateUser(UserDto userDto, Integer id) {
       Optional<User>user = userRepository.findById(id);
       if (user.isPresent()){
           user.get().setName(userDto.getName());
           user.get().setSurName(userDto.getSurName());
           user.get().setEmail(userDto.getEmail());
           user.get().setPassword(userDto.getPassword());
           return Optional.of(userRepository.save(user.get()));
       }else {
           return Optional.empty();
       }
    }

    @Override
    public ResponseEntity<User> get(int id) {
      Optional<User>user = userRepository.findById(id);
     if (!user.isPresent()){
         ResponseEntity.notFound().build();
     }
     return ResponseEntity.ok(user.get());
    }

    @Override
    public ResponseEntity<User> getByEmail(String emil) {
        Optional<User>user = userRepository.findByEmail(emil);
        if (user.isPresent()){
            get(user.get().getId());
        }
        return ResponseEntity.ok(user.get());
    }

    @Override
    public boolean userExist(String email) {
       return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
   public ResponseEntity  delete(int id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
