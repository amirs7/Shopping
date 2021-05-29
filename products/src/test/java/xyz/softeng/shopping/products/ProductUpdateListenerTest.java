package xyz.softeng.shopping.products;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.softeng.shopping.products.application.ProductUpdateListener;
import xyz.softeng.shopping.products.domain.Product;

import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductUpdateListenerTest {
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductUpdateListener productUpdateListener;

    @Test
    void testProductUpdateIsRaised() {
        Product product = new Product();
        productUpdateListener.onProductUpdate(product);
        verify(rabbitTemplate).convertAndSend(product);
    }
}
