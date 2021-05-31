package xyz.softeng.shopping.authserver.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "auth_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;
}
