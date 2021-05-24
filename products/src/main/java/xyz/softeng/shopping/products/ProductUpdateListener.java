package xyz.softeng.shopping.products;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
@NoArgsConstructor
public class ProductUpdateListener {
    @Autowired
    private StreamBridge streamBridge;

    @PostUpdate
    @PostPersist
    public void onProductUpdate(Product product) {
        streamBridge.send("products-out-0", product);
    }
}
