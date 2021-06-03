package xyz.softeng.configserver;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConstructorBinding;

import static lombok.AccessLevel.PUBLIC;

@AllArgsConstructor
@ConstructorBinding
@FieldDefaults(makeFinal = true, level = PUBLIC)
public class ExchangeProperties {
    String products;
    String users;
    String purchases;
}
