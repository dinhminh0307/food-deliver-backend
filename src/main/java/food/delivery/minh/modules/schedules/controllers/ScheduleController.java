package food.delivery.minh.modules.schedules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import food.delivery.minh.common.models.schedules.Schedule;
import food.delivery.minh.exceptions.PassedException;
import food.delivery.minh.modules.schedules.services.ScheduleService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @PostMapping("schedule/add")
    public ResponseEntity<?> addSchedule(@RequestBody Schedule schedule) {
        try {
            return ResponseEntity.ok(scheduleService.createSchedule(schedule));
        } catch (NoResourceFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
        }
    }

    @GetMapping("schedule/get/current")
    public ResponseEntity<?> getCurrentUserSchedule() {
        try {
            return ResponseEntity.ok().body(scheduleService.getCurrentUserSchedule());
        } catch (NoResourceFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        } catch (PassedException e) {
            return ResponseEntity.ok().build();
        }
    }
}
