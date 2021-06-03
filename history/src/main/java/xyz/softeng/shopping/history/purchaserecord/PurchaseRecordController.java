package xyz.softeng.shopping.history.purchaserecord;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PurchaseRecordController {
    private final PurchaseRecordRepository repository;

    @GetMapping
    public Iterable<PurchaseRecord> list() {
        return repository.findAll();
    }
}
