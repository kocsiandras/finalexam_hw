package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.dtos.LoggedInUserDTO;
import com.greenfoxacademy.finalexamhw.dtos.RegistrationDTO;
import com.greenfoxacademy.finalexamhw.models.Role;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

  final
  JWTServiceImpl jwtService;

  final
  BCryptPasswordEncoder bCryptPasswordEncoder;

  final
  UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JWTServiceImpl jwtService) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.jwtService = jwtService;
  }

  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public LoggedInUserDTO validateUser(RegistrationDTO registrationDTO) {
    User storedUser = userRepository.findByUsername(registrationDTO.getUsername());
    if(bCryptPasswordEncoder.matches(registrationDTO.getPassword(),storedUser.getPassword())){
      return new LoggedInUserDTO(registrationDTO.getUsername(), jwtService.createToken(storedUser.getUsername()));
    }
    return null;
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User findById(long id) {
    return userRepository.findById(id);
  }

  @Override
  public boolean existsById(long id) {
    return userRepository.existsById(id);
  }

  @Override
  public void deleteUser(long id) {
    userRepository.deleteById(id);
  }

  @Override
  public boolean isAdmin(User user) {
    for (Role role : user.getRoles()){
      if(role.getName().equals("admin")){
        return true;
      }
    }
    return false;
  }
}
