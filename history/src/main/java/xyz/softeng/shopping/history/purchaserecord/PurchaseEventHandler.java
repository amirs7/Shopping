package xyz.softeng.shopping.history.purchaserecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.softeng.shopping.common.events.PurchaseEvent;
import xyz.softeng.shopping.history.HistoryServiceProperties;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PurchaseEventHandler {
    private final PurchaseRecordRepository purchaseRecordRepository;
    private final PurchaseRecordMapper mapper;

    @RabbitListener(queues = "${history-service.purchase-queue}")
    public void savePurchase(PurchaseEvent event) {
        log.info("Handling purchase event: {}", event);
        PurchaseRecord purchaseRecord = mapper.fromEvent(event);
        purchaseRecordRepository.save(purchaseRecord);
    }

    @Bean
    public FanoutExchange purchaseExchange(HistoryServiceProperties properties) {
        return ExchangeBuilder.fanoutExchange(properties.getPurchaseExchange()).build();
    }

    @Bean
    public Queue purchaseQueue(HistoryServiceProperties properties) {
        return QueueBuilder.durable(properties.getPurchaseQueue()).build();
    }

    @Bean
    public Binding purchasesBinding(Queue purchaseQueue, FanoutExchange purchaseExchange) {
        return BindingBuilder.bind(purchaseQueue).to(purchaseExchange);
    }
}
