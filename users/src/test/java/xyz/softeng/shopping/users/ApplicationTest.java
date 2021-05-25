package xyz.softeng.shopping.users;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@TestPropertySource(properties = {
        "spring.cloud.stream.bindings.buys-in-0.destination=test-buys-topic",
        "spring.cloud.stream.bindings.users-out-0.destination=test-users-topic"
})
public class ApplicationTest {
    @Autowired
    private InputDestination inputDestination;

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void teardown() {
        userRepository.deleteAll();
    }

    @Test
    void testUserEventIsRaisedAfterUpdate() {
        User user = new User();
        user.setUsername("amir");
        user.setWealth(10_000);
        userRepository.save(user);
    }
}
