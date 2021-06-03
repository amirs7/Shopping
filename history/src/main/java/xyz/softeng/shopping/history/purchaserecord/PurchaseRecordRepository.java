package xyz.softeng.shopping.history.purchaserecord;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PurchaseRecordRepository extends CrudRepository<PurchaseRecord, Long> {
}
