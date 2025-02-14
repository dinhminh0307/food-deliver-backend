package food.delivery.minh.modules.tasks;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import food.delivery.minh.common.models.schedules.Schedule;

@Service
public class TimeCheckService {

    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = 6000) // Run every 60 seconds
    public void checkTimeAndDay() {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int hour = now.getHour();
        int minute = now.getMinute();

        // Fetch cached schedules
        List<Schedule> schedules = cacheManager.getCache("schedules").get("currentUserSchedule", List.class);
        if(schedules == null) {
            return;
        }
        for(Schedule s : schedules) {
            System.out.println("Schedule name: " + s.getName());
        }
        // Perform your logic here

        // now get current schedules of users
        
        // if the if the current days of week is different from the schedule, then check the isPassed field (1)
        // if the isPassed field is false or null, wait until the current days of week same day and same time then set to true 
        // and delete the schedule

        // check if the isPassed field is true, delete the schedule
        // if the current days of week is the same day of week with schedule and time is above  30mins, send email (2)
        
        // System.out.println("Current time: " + hour + ":" + minute + ", Day of week: " + dayOfWeek);
    }
}
