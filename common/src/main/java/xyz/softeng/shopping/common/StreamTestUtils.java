package xyz.softeng.shopping.common;

import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

public class StreamTestUtils {
    public static <T> T convert(Message<byte[]> message, Class<T> clazz) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return (T) converter.fromMessage(message, clazz);
    }
}
