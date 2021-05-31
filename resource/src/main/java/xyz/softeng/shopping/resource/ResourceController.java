package xyz.softeng.shopping.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public String test() {
        return "Hello";
    }
}
