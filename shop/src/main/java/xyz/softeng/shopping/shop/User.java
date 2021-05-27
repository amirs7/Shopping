package xyz.softeng.shopping.shop;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "domain_user")
public class User {
    @Id
    private Long id;

    private Integer wealth;
}
