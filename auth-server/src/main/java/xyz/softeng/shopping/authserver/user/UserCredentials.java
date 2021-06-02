package xyz.softeng.shopping.authserver.user;

import lombok.Data;

@Data
public class UserCredentials {
    private String username;
    private String password;
}
