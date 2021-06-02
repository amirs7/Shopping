package xyz.softeng.shopping.rating.rating;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RatingRepository extends CrudRepository<PurchaseRating, Long> {
    Optional<PurchaseRating> findByProductId(Long productId);

    Optional<PurchaseRating> findByProductIdAndUsername(Long productId, String username);
}
