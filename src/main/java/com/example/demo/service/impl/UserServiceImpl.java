package com.example.demo.service.impl;


import com.example.demo.mapper.Mapper;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    final RoleRepository roleRepository;

    final Mapper mapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
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
    public Optional<User> get(Integer id) {
      return userRepository.findById(Math.toIntExact(id));
    }

    @Override
    public Optional<User> save(User user) {
        User save = userRepository.save(user);
        return Optional.of(save);
    }


    @Override
    public Optional<User> getByEmail(String emil) {
        return userRepository.findByEmail(emil);
    }

    @Override
    public boolean existsByEmail(String email) {
       return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
