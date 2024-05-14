package com.example.demo.service;

import com.example.demo.entity.UserItem;
import com.example.demo.entity.enums.ERole;
import com.example.demo.exceptions.UserExistException;
import com.example.demo.model.User;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public UserItem create(SignupRequest userIn, ERole eRole) {
    UserItem user = new UserItem();
    user.setEmail(userIn.getEmail());
    user.setName(userIn.getFirstname());
    user.setLastname(userIn.getLastname());
    user.setUsername(userIn.getUsername());
    user.setPassword(passwordEncoder.encode(userIn.getPassword()));
    user.getRoles().add(eRole);

    try {
      log.info("Saving User {}", userIn.getEmail());
      return userRepository.save(user);
    } catch (Exception e) {
      log.error("Error during registration. {}", e.getMessage());
      throw new UserExistException(
          "The user " + user.getUsername() + " already exist. Please check credentials");
    }
  }

  public UserItem updateUser(User userDTO, Principal principal) {
    UserItem user = getUserByPrincipal(principal);
    user.setName(userDTO.getFirstname());
    user.setLastname(userDTO.getLastname());

    return userRepository.save(user);
  }

  public UserItem updateUserByAdmin(User user) {
    UserItem userItem = getUserById(user.getUserId());
    userItem.setName(user.getFirstname());
    userItem.setLastname(user.getLastname());

    return userRepository.save(userItem);
  }

  public UserItem getCurrentUser(Principal principal) {
    return getUserByPrincipal(principal);
  }

  private UserItem getUserByPrincipal(Principal principal) {
    String username = principal.getName();
    return userRepository.findUserItemByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username " + username));

  }

  public UserItem getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public UserItem getUserByUsername(String username) {
    return userRepository.findUserItemByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username " + username));
  }

  public boolean isAdmin(Long userId) {
    UserItem userModel = getUserById(userId);
    String role = "";
    for (ERole e : userModel.getRoles()) {
      role = String.valueOf(e);
    }
    return role.equals("ROLE_ADMIN");
  }

  public boolean isDiet2(Long userId) {
    UserItem userModel = getUserById(userId);
    String role = "";
    for (ERole e : userModel.getRoles()) {
      role = String.valueOf(e);
    }
    return role.equals("ROLE_DIET");
  }
  public boolean isDiet(Principal principal) {
    UserItem userModel = getUserByPrincipal(principal);
    String role = "";
    for (ERole e : userModel.getRoles()) {
      role = String.valueOf(e);
    }
    return role.equals("ROLE_DIET");
  }

  public boolean isUser(UserItem user) {
    String role = "";
    for (ERole e : user.getRoles()) {
      role = String.valueOf(e);
    }
    return role.equals("ROLE_USER");
  }

  public List<UserItem> getClients() {
    return userRepository.findAll()
        .stream().filter(user -> isUser(user))
        .collect(Collectors.toList());
  }
}
