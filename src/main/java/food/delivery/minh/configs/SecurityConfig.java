package food.delivery.minh.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disable CSRF if needed (optional)
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/").permitAll()  // Allow access to the root endpoint
                .anyRequest().authenticated()     // Secure other endpoints
            )
            .httpBasic(); // Enable basic HTTP security (optional)
        return http.build();
    }
}
