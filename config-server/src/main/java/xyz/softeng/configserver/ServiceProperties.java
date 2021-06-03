package xyz.softeng.configserver;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PUBLIC;

@AllArgsConstructor
@ConstructorBinding
@FieldDefaults(makeFinal = true, level = PUBLIC)
public class ServiceProperties {
    Integer port;

    String host;
}
