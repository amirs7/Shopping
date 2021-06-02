package xyz.softeng.shopping.common.configuration;

import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

@Component
public class RabbitExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
    @Override
    public boolean isFatal(Throwable throwable) {
        if (throwable instanceof ListenerExecutionFailedException)
            return true;
        return false;
    }
}
