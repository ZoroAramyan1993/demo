package com.example.demo.controller;




import com.example.demo.dto.SignUp;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired(required = true)
    AuthenticationManager authenticationManager;

    @Autowired
     UserRepository userRepository;


    @Autowired
    RoleRepository roleRepository;


    @GetMapping("/get/{id}")
    ResponseEntity<User>get(@PathVariable("id") Integer id){
        return userService.get(id);
    }

//    @GetMapping("/get/{email}")
//    ResponseEntity<User>getUser(@PathVariable("email") String email){
//        return userService.getByEmail(email);
//    }

    @GetMapping("/get")
    List<User> getAll(){
        return userService.getAll();
    }


    @PostMapping("/save")
    ResponseEntity<?> saveUser(@RequestBody UserDto userDto){

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
        return ResponseEntity.ok(user);
    }


//    @PostMapping("/authenticate")
//    String login( @RequestBody SignUp signUp){
////        if (signUp.getEmail().equals(user.getEmail()) && signUp.getPassword().equals(user.getPassword())){
////            return "home";
////        }
//        return "home";
//    }

    @PostMapping("/authenticate")
   ResponseEntity<?> authenticateUser( @RequestBody SignUp signUp){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signUp.getEmail(),signUp.getPassword()));
          SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok().build();

    }

    @PutMapping("/update")
    ResponseEntity<User> updateUser(@Validated @RequestBody UserDto userDto,@RequestParam("userId") Integer userId){
        Optional<User>user = userRepository.findById(userId);
        if (!user.isPresent()){
          return   ResponseEntity.notFound().build();
        }
//        userRepository.findById(userId).map(user-> {
//            user.setName(userDto.getName());
//            user.setSurName(userDto.getSurName());
//            user.setEmail(userDto.getEmail());
//            user.setPassword(userDto.getPassword());
//            userRepository.save(user);
//            return ResponseEntity.ok(user);
//        });
       // return ResponseEntity.notFound().build();

//          Optional<User>user = userRepository.findById(userId);
       //   if (user.isPresent()){
              user.get().setName(userDto.getName());
              user.get().setSurName(userDto.getSurName());
              user.get().setEmail(userDto.getEmail());
              user.get().setPassword(userDto.getPassword());
              userRepository.save(user.get());
        //  }

          return ResponseEntity.ok(user.get());
      //  return userService.updateUser(userDto,userId);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity deleteUser(@PathVariable("id") Integer id){
        return userService.delete(id);
    }

    @GetMapping("/getEmail")
    public ResponseEntity<User>getUser (@RequestParam String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/role")
    public ResponseEntity<RoleName>getUserRole(@RequestParam String username){
        Optional<User> user = userRepository.findByEmail(username);
        if(user.isPresent()){
            if (this.roleRepository.getRoleId(user.get().getId()) == 1){
                return ResponseEntity.ok(RoleName.USER);
            }
            return ResponseEntity.ok(RoleName.MANAGER);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
