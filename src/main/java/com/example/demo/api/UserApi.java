package com.example.demo.api;

import com.example.demo.model.User;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @CrossOrigin
    @GetMapping("/clients")
    public ResponseEntity<List<User>> getClients();
}
