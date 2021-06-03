package xyz.softeng.shopping.rating.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RatingController {
    private final RatingRepository repository;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public PurchaseRating rateProduct(Authentication authentication, @RequestParam long productId, @RequestParam int value) {
        PurchaseRating rating = repository.findByProductIdAndUsername(productId, authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Purchased"));
        if (rating.isRated())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Rated");
        rating.setValue(value);
        rating.setRated(true);
        return repository.save(rating);
    }

    @GetMapping
    public Iterable<PurchaseRating> list() {
        return repository.findAll();
    }

    @GetMapping("/{productId}")
    public PurchaseRating getProductRating(@PathVariable Long productId) {
        return repository.findByProductId(productId).orElseThrow();
    }
}
