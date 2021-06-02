package xyz.softeng.shopping.products;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import xyz.softeng.shopping.common.components.CommonComponents;

@ConfigurationPropertiesScan
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(scanBasePackageClasses = {ProductService.class, CommonComponents.class})
public class ProductService {
    public static void main(String[] args) {
        SpringApplication.run(ProductService.class, args);
    }
}
