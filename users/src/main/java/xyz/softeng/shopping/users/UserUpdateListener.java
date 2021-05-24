package xyz.softeng.shopping.users;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
@NoArgsConstructor
public class UserUpdateListener {
    @Autowired
    private StreamBridge streamBridge;

    @PostUpdate
    @PostPersist
    public void onUserUpdate(User user){
        streamBridge.send("users-out-0", user);
    }
}
