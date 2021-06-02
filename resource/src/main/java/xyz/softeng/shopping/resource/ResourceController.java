package xyz.softeng.shopping.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public class ResourceController {
    @GetMapping("/customer")
    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public String customer() {
        return "customer";
    }

    @GetMapping("/vendor")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public String vendor() {
        return "vendor";
    }

    @GetMapping("/admin")
    public String test() {
        return "admin";
    }
}
