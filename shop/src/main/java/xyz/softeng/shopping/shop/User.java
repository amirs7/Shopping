package xyz.softeng.shopping.shop;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private Long id;

    private Integer wealth;
}
