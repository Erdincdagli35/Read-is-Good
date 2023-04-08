package com.ed.RIG.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CustomerLoginRequest {
    private String username;
    private String password;
}
