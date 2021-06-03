package xyz.softeng.shopping.rating;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import xyz.softeng.shopping.rating.rating.PurchaseRating;
import xyz.softeng.shopping.rating.rating.RatingRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RatingRepositoryTest {
    @Autowired
    private RatingRepository ratingRepository;

    @Test
    void test() {
        PurchaseRating rating = new PurchaseRating();
        rating.setUsername("amir");
        rating.setProductId(10L);
        rating.setValue(10);
        rating.setRated(true);

        ratingRepository.save(rating);

        PurchaseRating savedRating = ratingRepository.findByProductIdAndUsername(10L, "amir")
                .orElseThrow();

        assertThat(savedRating).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(rating);
        assertThat(savedRating.getId()).isNotNull();
    }
}
