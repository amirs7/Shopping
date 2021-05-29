package xyz.softeng.shopping.users.domain;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "domain_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String username;

    private Integer wealth;

    public void decreaseWealth(int amount) {
        if (amount > wealth)
            throw new IllegalArgumentException();
        wealth -= amount;
    }
}
