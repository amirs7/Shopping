package xyz.softeng.shopping.rating.rating;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.PurchaseEvent;

@Mapper
@Component
public interface PurchaseRatingMapper {
    PurchaseRating fromEvent(PurchaseEvent event);
}
