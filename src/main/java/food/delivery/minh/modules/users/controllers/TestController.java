package food.delivery.minh.modules.users.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class TestController {
    
    @GetMapping("")
    public String getMethodName() {
        return "Hello World";
    }
    
}
