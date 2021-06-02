package xyz.softeng.shopping.history.purchaserecord;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PurchaseRecordController {
    private final PurchaseRecordRepository repository;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public PurchaseRecord rateProduct(Authentication authentication, @RequestParam long productId, @RequestParam int value) {
        PurchaseRecord rating = repository.findByProductIdAndUsername(productId, authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Purchased"));
        if (rating.isRated())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Rated");
        rating.setValue(value);
        rating.setRated(true);
        return repository.save(rating);
    }

    @GetMapping
    public Iterable<PurchaseRecord> list() {
        return repository.findAll();
    }

    @GetMapping("/{productId}")
    public PurchaseRecord getProductRating(@PathVariable Long productId) {
        return repository.findByProductId(productId).orElseThrow();
    }
}
