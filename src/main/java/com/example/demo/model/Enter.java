package com.example.demo.model;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Enter {

    private Long enterId;
    @NotEmpty
    private Long userId;

}
