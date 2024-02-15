package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecipeSaveNotFoundException extends RuntimeException {
    public RecipeSaveNotFoundException(String msg) {
        super(msg);

    }
}
