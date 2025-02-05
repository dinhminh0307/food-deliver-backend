package food.delivery.minh.common.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import food.delivery.minh.common.auth.jwt.JwtRequestFilter;

@Service
public class RestApiService {
    private final RestTemplate restTemplate;
    private final JwtRequestFilter authFilter;

    public RestApiService(RestTemplate restTemplate, JwtRequestFilter authFilter) {
        this.restTemplate = restTemplate;
        this.authFilter = authFilter;
    }

    /**
     * Generic GET request without needing to specify type.
     */
    public <T> ResponseEntity<T> getRequest(String url, Class<T> responseType) {
        try {
            HttpHeaders headers = createHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        } catch (Exception ex) {
            System.err.println("GET request failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Generic POST request without needing to specify type.
     */
    public <T> ResponseEntity<T> postRequest(String url, Object body, Class<T> responseType) {
        try {
            HttpHeaders headers = createHeaders();
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        } catch (Exception ex) {
            System.err.println("POST request failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Generic PUT request without needing to specify type.
     */
    public <T> ResponseEntity<T> putRequest(String url, Object body, Class<T> responseType) {
        try {
            HttpHeaders headers = createHeaders();
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
        } catch (Exception ex) {
            System.err.println("PUT request failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Generic DELETE request without needing to specify type.
     */
    public <T> ResponseEntity<T> deleteRequest(String url, Class<T> responseType) {
        try {
            HttpHeaders headers = createHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
        } catch (Exception ex) {
            System.err.println("DELETE request failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Helper method to create headers with JWT token.
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String token = authFilter.getBrowserToken();
        headers.add("Cookie", "Authorization=" + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
