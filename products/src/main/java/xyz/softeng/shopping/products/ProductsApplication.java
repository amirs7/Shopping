package xyz.softeng.shopping.products;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import xyz.softeng.shopping.common.components.CommonComponents;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(scanBasePackageClasses = {ProductsApplication.class, CommonComponents.class})
public class ProductsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }
}
