package com.example.demo.facade;

import com.example.demo.entity.UserItem;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public User userModelToUserDTO(UserItem userItem) {
        User user = new User();
        user.setUserId(userItem.getUserId());
        user.setFirstname(userItem.getName());
        user.setLastname(userItem.getLastname());
        user.setUsername(userItem.getUsername());
        return user;
    }

}
