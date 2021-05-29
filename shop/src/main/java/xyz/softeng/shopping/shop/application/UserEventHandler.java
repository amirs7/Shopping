package xyz.softeng.shopping.shop.application;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import xyz.softeng.shopping.shop.domain.User;
import xyz.softeng.shopping.shop.domain.UserRepository;

//@Component
@RequiredArgsConstructor
public class UserEventHandler {
    private final UserRepository userRepository;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue("shop-users-in"),
                    exchange = @Exchange(value = "${shopping.rabbit.exchanges.users}", declare = "false"))
    )
    public void saveNewProduct(User user) {
        userRepository.save(user);
    }
}
