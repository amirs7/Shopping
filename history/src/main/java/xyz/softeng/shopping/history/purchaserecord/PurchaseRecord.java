package xyz.softeng.shopping.history.purchaserecord;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class PurchaseRecord {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Long productId;

    private Integer cost;

    @CreationTimestamp
    private LocalDateTime datetime;
}
