package xyz.softeng.shopping.rating.rating;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(indexes = @Index(columnList = "username, productId", unique = true))
public class PurchaseRating {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Long productId;

    private Integer value;

    private boolean rated = false;
}
