package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.role.RoleName;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    UserService userService;


    @Autowired
    RoleRepository roleRepository;


    //url i mej get mi gri
    @GetMapping("/user/{id}")
    ResponseEntity<User> get(@PathVariable("id") Integer id) {
        User user = userService.get(id).orElseThrow(() -> new RuntimeException("Resource not found"));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/user")
    ResponseEntity<List<User>> getAll() {
        List<User> all = userService.getAll();
        return ResponseEntity.ok(all);
    }


    @PostMapping("/user")
    ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {

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
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user")
    ResponseEntity<User> updateUser(@Validated @RequestBody UserDto userDto, @RequestParam("userId") Integer userId) {
        Optional<User> user = userService.get(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        user.get().setName(userDto.getName());
        user.get().setSurName(userDto.getSurName());
        user.get().setEmail(userDto.getEmail());
        user.get().setPassword(userDto.getPassword());
        userService.save(user.get());
        return ResponseEntity.ok(user.get());
    }

    @DeleteMapping("/user/{id}")
    ResponseEntity deleteUser(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/role")
    public ResponseEntity<RoleName> getUserRole(@RequestParam String username) {
        Optional<User> user = userService.getByEmail(username);
        if (user.isPresent()) {
            if (this.roleRepository.getRoleId(user.get().getId()) == 1) {
                return ResponseEntity.ok(RoleName.USER);
            }
            return ResponseEntity.ok(RoleName.MANAGER);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
