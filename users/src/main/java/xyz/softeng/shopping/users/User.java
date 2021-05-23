package xyz.softeng.shopping.users;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String firstname;

    private String lastname;

    private String address;

    private Integer wealth;
}
