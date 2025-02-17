package food.delivery.minh.modules.emails.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import food.delivery.minh.common.models.schedules.Schedule;
import food.delivery.minh.modules.emails.services.EmailService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/")
public class EmailController {
    @Autowired
    EmailService emailService;

    @GetMapping("email/cart")
    public ResponseEntity<?> sendAddToCartConfirm(@RequestParam int cartId) {
        emailService.sendAddToCartConfirm(cartId);
        return ResponseEntity.ok(Void.class);
    }

    @PostMapping("email/schedule")
    public ResponseEntity<?> sendScheduleReminder(@RequestBody Schedule schedule) {
        emailService.sendScheduleReminder(schedule);
        return ResponseEntity.ok(Void.class);
    }
}
