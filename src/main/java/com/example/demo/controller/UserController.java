package com.example.demo.controller;



import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;



    @GetMapping("get{id}")
   ResponseEntity<User>get(Integer id){
       return userService.get(id);
   }

    @GetMapping("/get{email}")
ResponseEntity<User>getUser(@PathVariable("email") String email){
        return userService.getByEmail(email);
    }

    @GetMapping("/get")
    List<User> getAll(){
      return userService.getAll();
}


   @PostMapping("/save")
   ResponseEntity<?>saveUser(@Validated @RequestBody UserDto userDto){
//      User user = new User(userDto.getName(),userDto.getSurName(),userDto.getEmail(),userDto.getPassword());
//      userRepository.save(user);
//       return ResponseEntity.ok(user);
      return userService.addUser(userDto);
   }



      @PutMapping("/update")
   Optional <User> updateUser(@Validated @RequestBody UserDto userDto, Integer userId){
//        userService.get(userId).map(user-> {
//            user.setName(userDto.getName());
//            user.setSurName(userDto.getSurName());
//            user.setEmail(userDto.getEmail());
//            user.setPassword(userDto.getPassword());
//            userService.addUser(user);
//            return ResponseEntity.ok(user);
//        });
//        return ResponseEntity.notFound().build();

//          Optional<User>user = userRepository.findById(userId);
//          if (user.isPresent()){
//              user.get().setName(userDto.getName());
//              user.get().setSurName(userDto.getSurName());
//              user.get().setEmail(userDto.getEmail());
//              user.get().setPassword(userDto.getPassword());
//              userRepository.save(user.get());
//          }
//
//          return ResponseEntity.ok(user);
         return userService.updateUser(userDto,userId);
     }

     @DeleteMapping("/delete{id}")
     ResponseEntity deleteUser(@PathVariable("id") Integer id){
//       userRepository.deleteById(id);
//        return ResponseEntity.ok().build();
         return userService.delete(id);
     }
}