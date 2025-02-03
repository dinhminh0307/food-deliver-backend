package food.delivery.minh.common.api;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import food.delivery.minh.common.auth.jwt.JwtRequestFilter;

@Service
public class RestApiService<T> {
    private final RestTemplate restTemplate;
    private final JwtRequestFilter authFilter;

    public RestApiService(RestTemplate restTemplate, JwtRequestFilter authFilter) {
        this.restTemplate = restTemplate;
        this.authFilter = authFilter;
    }

    /**
     * Generic GET request
     */
    public ResponseEntity<T> getRequest(String url, Class<T> responseType) {
        try {
            String token = authFilter.getBrowserToken();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", "Authorization=" + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        } catch (Exception ex) {
            System.err.println("GET request failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Generic POST request
     */
    public ResponseEntity<T> postRequest(String url, T body, Class<T> responseType) {
        try {
            String token = authFilter.getBrowserToken();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", "Authorization=" + token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<T> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        } catch (Exception ex) {
            System.err.println("POST request failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Generic PUT request
     */
    public ResponseEntity<T> putRequest(String url, T body, Class<T> responseType) {
        try {
            String token = authFilter.getBrowserToken();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", "Authorization=" + token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<T> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
        } catch (Exception ex) {
            System.err.println("PUT request failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

