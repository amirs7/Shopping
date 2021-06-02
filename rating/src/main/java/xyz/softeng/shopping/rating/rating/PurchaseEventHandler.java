package xyz.softeng.shopping.rating.rating;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.softeng.shopping.common.events.PurchaseEvent;
import xyz.softeng.shopping.rating.RatingServiceProperties;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PurchaseEventHandler {
    private final RatingRepository ratingRepository;
    private final PurchaseRatingMapper mapper;

    @RabbitListener(queues = "${rating-service.purchase-queue}")
    public void savePurchase(PurchaseEvent event) {
        log.info("Handling purchase event: {}", event);
        PurchaseRating rating = mapper.fromEvent(event);
        ratingRepository.save(rating);
    }

    @Bean
    public FanoutExchange purchaseExchange(RatingServiceProperties properties) {
        return ExchangeBuilder.fanoutExchange(properties.getPurchaseExchange()).build();
    }

    @Bean
    public Queue purchaseQueue(RatingServiceProperties properties) {
        return QueueBuilder.durable(properties.getPurchaseQueue()).build();
    }

    @Bean
    public Binding purchasesBinding(Queue purchaseQueue, FanoutExchange purchaseExchange) {
        return BindingBuilder.bind(purchaseQueue).to(purchaseExchange);
    }
}
