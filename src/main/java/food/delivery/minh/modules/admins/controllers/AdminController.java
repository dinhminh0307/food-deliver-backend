package food.delivery.minh.modules.admins.controllers;

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

import food.delivery.minh.common.auth.jwt.JwtUtil;
import food.delivery.minh.common.dto.AccountDTO;
import food.delivery.minh.common.models.accounts.Admin;
import food.delivery.minh.modules.admins.services.AdminService;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("signupAdmin")
    public ResponseEntity<?> createAccount(@RequestBody Admin admin) {
        Admin savedUser = adminService.createAccount(admin);
        AccountDTO accountDTO = new AccountDTO(savedUser.getAccount_id(), savedUser.getEmail(), savedUser.getLastName()
                                , savedUser.getFirstName(), savedUser.getPhoneNumber(), savedUser.getDob(), savedUser.getImageUrl());
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("loginAdmin")
    public ResponseEntity<?> login(@RequestBody Admin admin, HttpServletResponse response) {
        try {
            // Authenticate using raw password provided by user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(admin.getEmail(), admin.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtUtil.generateToken(admin.getEmail());

                    ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", token)
                            .httpOnly(true)
                            .secure(false)
                            .path("/")
                            .maxAge(60 * 60)
                            .sameSite("Lax")
                            .build();

                response.addHeader("Set-Cookie", jwtCookie.toString());

                return ResponseEntity.ok("Login Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    
    }
}
