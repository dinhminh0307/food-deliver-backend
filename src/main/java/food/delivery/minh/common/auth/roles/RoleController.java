package food.delivery.minh.common.auth.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import food.delivery.minh.common.enums.RoleEnum.ROLE;


@RestController
@RequestMapping("/auth")
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping("")
    public ResponseEntity<?> checkUser(@RequestParam String email) {
        try {
            ROLE result = roleService.checkRole(email);
            return ResponseEntity.ok(result.name()); // return the role to string
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong email");
        }
    }
    
}
