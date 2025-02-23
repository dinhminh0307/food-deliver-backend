package food.delivery.minh.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import food.delivery.minh.common.auth.jwt.JwtUtil;
import food.delivery.minh.common.dto.response.AccountDTO;
import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.exceptions.PassedException;
import food.delivery.minh.modules.schedules.services.ScheduleService;
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

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CacheManager cacheManager;

    @PostMapping("signup")
    public ResponseEntity<?> createAccount(@RequestBody User user) {
        User savedUser = userService.createAccount(user);
        AccountDTO accountDTO = new AccountDTO(savedUser.getAccount_id(), savedUser.getEmail(), savedUser.getLastName()
                                , savedUser.getFirstName(), savedUser.getPhoneNumber(), savedUser.getDob(), savedUser.getImageUrl());
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        try {
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
                        .maxAge(60 * 60)
                        .sameSite("Lax")
                        .build();

                response.addHeader("Set-Cookie", jwtCookie.toString());

                // find user by email
                user = userService.findByEmail(user.getEmail());

                // Populate the cache with the current user's schedule
                if (cacheManager.getCache("schedules") != null) {
                    cacheManager.getCache("schedules").put("currentUserSchedule", scheduleService.getCurrentUserSchedule(user));
                }
                
                return ResponseEntity.ok("Login Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
            }
        }
        catch (PassedException e) {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("currentUser")
    public ResponseEntity<?> getCurrentUserData() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PutMapping("/currentUser/update")
    public ResponseEntity<?> updateCurrentUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateCurrentUser(user));
    }

    @GetMapping("user/get")
    public ResponseEntity<?> getProductsByType(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getAllUser(pageable));
    }

    @GetMapping("user/id")
    public ResponseEntity<?> getUserById(@RequestParam int userId) {
        try {
            return ResponseEntity.ok(userService.getUserById(userId));
        } catch (NoResourceFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getBody());
        }
    }
}