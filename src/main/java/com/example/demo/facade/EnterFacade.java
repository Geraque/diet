package com.example.demo.facade;

import com.example.demo.entity.EnterItem;
import com.example.demo.model.Enter;
import org.springframework.stereotype.Component;

@Component
public class EnterFacade {

    public Enter enterToEnterDTO(EnterItem enter) {
        Enter enterDTO = new Enter();
        enterDTO.setEnterId(enter.getEnterId());
        enterDTO.setUserId(enter.getUserId());

        return enterDTO;
    }

}
