package xyz.softeng.shopping.history.purchaserecord;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.PurchaseEvent;

@Mapper
@Component
public interface PurchaseRecordMapper {
    PurchaseRecord fromEvent(PurchaseEvent event);
}
