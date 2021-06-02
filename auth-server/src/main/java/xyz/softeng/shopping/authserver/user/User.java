package xyz.softeng.shopping.authserver.user;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

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

    @NaturalId
    private String username;

    private String password;

    private Role role;

    public enum Role {
        ADMIN,
        VENDOR,
        CUSTOMER
    }
}
