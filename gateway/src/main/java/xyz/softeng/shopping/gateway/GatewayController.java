package xyz.softeng.shopping.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class GatewayController {
    private final GatewayProperties properties;
    private final RestTemplate restTemplate = new RestTemplate();

    @ResponseBody
    @RequestMapping("/{service}/**")
    public ResponseEntity<String> dispatch(@PathVariable String service, RequestEntity<?> requestEntity) {
        if (!properties.getServices().containsKey(service))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        URI uri = UriComponentsBuilder.fromHttpUrl(properties.getServices().get(service))
                .path(requestEntity.getUrl().getPath().replace("/" + service, ""))
                .query(requestEntity.getUrl().getQuery())
                .build()
                .toUri();

        RequestEntity<?> modifiedRequest = new RequestEntity<>(
                requestEntity.getBody(),
                requestEntity.getHeaders(),
                requestEntity.getMethod(),
                uri,
                requestEntity.getType());
        return restTemplate.exchange(modifiedRequest, String.class);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleClientException(HttpClientErrorException exception) {
        log.error("Request error", exception);
        return new ResponseEntity<>(exception.getMessage(), exception.getStatusCode());
    }
}
