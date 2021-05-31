package xyz.softeng.shopping.users.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import xyz.softeng.shopping.users.application.UserUpdateListener;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "domain_user")
@EntityListeners(UserUpdateListener.class)
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String username;

    private Integer wealth;

    public User(String username, Integer wealth) {
        this.username = username;
        this.wealth = wealth;
    }

    public void decreaseWealth(int amount) {
        if (amount > wealth)
            throw new IllegalArgumentException();
        wealth -= amount;
    }
}
