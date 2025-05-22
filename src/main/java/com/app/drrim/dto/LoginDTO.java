package com.app.drrim.dto;

import com.app.drrim.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class LoginDTO {
    public String email;
    public String password;

    public LoginDTO(User obj) {
        this.email = obj.getEmail();
        this.password = obj.getPassword();
    }
}
