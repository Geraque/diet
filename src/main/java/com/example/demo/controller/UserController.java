package com.example.demo.controller;

import com.example.demo.api.UserApi;
import com.example.demo.entity.UserItem;
import com.example.demo.facade.UserFacade;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.validations.ResponseErrorValidation;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController implements UserApi {

  private UserService userService;
  private UserFacade userFacade;
  private ResponseErrorValidation responseErrorValidation;

  @Override
  public ResponseEntity<User> getCurrentUser(Principal principal) {
    UserItem user = userService.getCurrentUser(principal);
    User userDTO = userFacade.userModelToUserDTO(user);

    return new ResponseEntity<>(userDTO, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<User> getUserProfile(String userId) {
    UserItem user = userService.getUserById(Long.parseLong(userId));
    User userDTO = userFacade.userModelToUserDTO(user);

    return new ResponseEntity<>(userDTO, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<User> getUserByUsername(String username) {
    UserItem user = userService.getUserByUsername(username);
    User userDTO = userFacade.userModelToUserDTO(user);

    return new ResponseEntity<>(userDTO, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> updateUser(User userDTO, BindingResult bindingResult,
      Principal principal) {
    ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
      if (!ObjectUtils.isEmpty(errors)) {
          return errors;
      }

    UserItem user = userService.updateUser(userDTO, principal);

    User userUpdated = userFacade.userModelToUserDTO(user);
    return new ResponseEntity<>(userUpdated, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> updateUserByAdmin(User userDTO, BindingResult bindingResult) {
    ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
      if (!ObjectUtils.isEmpty(errors)) {
          return errors;
      }

    UserItem user = userService.updateUserByAdmin(userDTO);

    User userUpdated = userFacade.userModelToUserDTO(user);
    return new ResponseEntity<>(userUpdated, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Boolean> isAdmin(String userId) {
    Boolean bool = userService.isAdmin(Long.parseLong(userId));
    return new ResponseEntity<>(bool, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Boolean> isDiet2(String userId) {
    Boolean bool = userService.isDiet2(Long.parseLong(userId));
    return new ResponseEntity<>(bool, HttpStatus.OK);
  }
  @Override
  public ResponseEntity<Boolean> isDiet(Principal principal) {
    Boolean bool = userService.isDiet(principal);
    return new ResponseEntity<>(bool, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<User>> getClients() {
    List<User> ingredients = userService.getClients()
        .stream()
        .map(userFacade::userModelToUserDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<>(ingredients, HttpStatus.OK);
  }

}