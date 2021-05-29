package xyz.softeng.configserver;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.Valid;

import static lombok.AccessLevel.PUBLIC;

@ConstructorBinding
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PUBLIC)
public class RabbitProperties {
    @Valid
    ExchangeProperties exchange;
}
