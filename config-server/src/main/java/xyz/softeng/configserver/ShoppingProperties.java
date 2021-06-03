package xyz.softeng.configserver;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

import static lombok.AccessLevel.PUBLIC;

@Validated
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("eshop")
@FieldDefaults(makeFinal = true, level = PUBLIC)
public class ShoppingProperties {
    @Valid
    ExchangeProperties exchange;

    @NotEmpty
    String authServerUri;

    Map<String, ServiceProperties> services;
}
