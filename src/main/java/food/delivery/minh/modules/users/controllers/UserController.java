package food.delivery.minh.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import food.delivery.minh.common.configs.JwtUtil;
import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.modules.users.services.UserService;
import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/")

public class UserController {
    @Autowired
    UserService userService;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("signup")
    public ResponseEntity<?> createAccount(@RequestBody User user) {
        return ResponseEntity.ok(userService.createAccount(user));
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        try {
            System.out.println("Hello");
            // Authenticate using raw password provided by user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtUtil.generateToken(user.getEmail());

                ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", token)
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(7 * 24 * 60 * 60)
                        .sameSite("Lax")
                        .build();

                response.addHeader("Set-Cookie", jwtCookie.toString());

                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
}
