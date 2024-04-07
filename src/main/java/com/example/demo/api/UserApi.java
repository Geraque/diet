package com.example.demo.api;

import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("api/user")
@CrossOrigin
public interface UserApi {

    @GetMapping("/")
    public ResponseEntity<User> getCurrentUser(Principal principal);

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable("userId") String userId);

    @CrossOrigin
    @PostMapping("/get/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username);

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, BindingResult bindingResult, Principal principal);

    @CrossOrigin
    @PostMapping("/updateByAdmin")
    public ResponseEntity<Object> updateUserByAdmin(@Valid @RequestBody User user, BindingResult bindingResult);

    @CrossOrigin
    @PostMapping("/isAdmin/{userId}")
    public ResponseEntity<Boolean> isAdmin(@PathVariable("userId") String userId);

    @CrossOrigin
    @PostMapping("/isDiet2/{userId}")
    public ResponseEntity<Boolean> isDiet2(@PathVariable("userId") String userId);

    @CrossOrigin
    @PostMapping("/isDiet")
    public ResponseEntity<Boolean> isDiet(Principal principal);
}
