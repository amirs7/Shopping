package xyz.softeng.shopping.products;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.TestPropertySource;
import xyz.softeng.shopping.common.StreamTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@TestPropertySource(properties = {
        "spring.cloud.stream.bindings.buys-in-0.destination=test-buys-topic",
        "spring.cloud.stream.bindings.products-out-0.destination=test-products-topic"
})
class ApplicationTest {
    @Autowired
    private InputDestination inputDestination;

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void teardown() {
        productRepository.deleteAll();
    }

    @Test
    void testProductEventIsRaisedAfterSave() {
        productRepository.save(new Product("test-pc", 1000));

        var message = outputDestination.receive(0, "test-products-topic");
        Product event = StreamTestUtils.convert(message, Product.class);
        assertThat(event).isNotNull();
        assertThat(event.getName()).isEqualTo("test-pc");
        assertThat(event.getPrice()).isEqualTo(1000);
    }

    @Test
    void testBuyEventIsProcessed() {
        Product product = productRepository.save(new Product("test-pc", 1000));
        Message<BuyEvent> message = MessageBuilder.withPayload(new BuyEvent(product.getId())).build();

        inputDestination.send(message, "test-buys-topic");

        product = productRepository.findById(product.getId()).orElseThrow();
        assertThat(product.getBuyCount()).isOne();
    }
}
