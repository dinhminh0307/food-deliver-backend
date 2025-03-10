package food.delivery.minh.common.auth.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

     @Autowired
    private AppUserDetailsService userDetailService;

    private String browserToken;

    private String email;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Retrieve the token from the cookie
        String token = null;
        String email = null;

        Cookie[] cookies = request.getCookies();
        // System.out.println("JwtAuthFilter: doFilterInternal called");

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    setBrowserToken(token);
                    email = jwtUtil.extractEmail(token);
                    setUserEmail(email);
                }
            }
        } else {
            System.out.println("No cookies found in request");
        }

        // If the token is valid and no authentication is set in the context
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailService.loadUserByUsername(email);

            // Validate token and set authentication
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    public String getBrowserToken()
    {
        return this.browserToken;
    }

    public void setBrowserToken(String browserToken)
    {
        this.browserToken = browserToken;
    }

    public String getUserEmail() {
        return this.email;
    }

    public void setUserEmail(String email) {
        this.email = email;
    }
}
