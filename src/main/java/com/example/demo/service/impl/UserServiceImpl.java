package com.example.demo.service.impl;


import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.role.RoleName;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<User> addUser(UserDto userDto) {
        User user = new User(userDto.getName(),userDto.getSurName(),userDto.getEmail(),userDto.getPassword());
        if(user.getEmail().equals("manager777")) {
            Role userRole = roleRepository.findByRoleName(RoleName.MANAGER).
                    orElseThrow(() -> new ApiException("manager role not set"));
            user.setRoles(Collections.singleton(userRole));
        }else{
            Role clientRole = roleRepository.findByRoleName(RoleName.USER).
                    orElseThrow(() -> new ExpressionException("user Role not set"));
            user.setRoles(Collections.singleton(clientRole));
        }
        userRepository.save(user);
        URI location  = ServletUriComponentsBuilder.
                fromCurrentContextPath().path("/user/{id}").
                buildAndExpand(user.getEmail()).toUri();
        //return ResponseEntity.created(location).body(new ApiResponse(true, "Client created successfull"));
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
    public ResponseEntity<User> get(Integer id) {
      Optional<User>user = userRepository.findById(Math.toIntExact(id));
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
   public ResponseEntity  delete(Integer id) {
        userRepository.deleteById(Math.toIntExact(id));
        return ResponseEntity.ok().build();
    }
}
