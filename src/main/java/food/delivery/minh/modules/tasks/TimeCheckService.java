package food.delivery.minh.modules.tasks;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import food.delivery.minh.common.api.RestApiService;
import food.delivery.minh.common.models.schedules.Schedule;

@Service
public class TimeCheckService {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RestApiService restApiService;

    private final static String _DELETE_SCHEDULE_API = "http://localhost:8080/schedule/delete?scheduleId=";

    private final static String _SEND_REMIND_API = "http://localhost:8080/email/schedule";

    @Scheduled(fixedRate = 6000) // Run every 60 seconds
    public void checkTimeAndDay() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();
        // Perform your logic here

        // now get current schedules of users
        // Fetch cached schedules
        List<Schedule> schedules = cacheManager.getCache("schedules").get("currentUserSchedule", List.class);
        if(schedules == null) {
            return;
        }
        
        // if the if the current days of week is different from the schedule, then check the isPassed field (1)
        for(Schedule s : schedules) {
            if(!dayOfWeek.equals(s.getDayOfWeek()) && !s.isPassed()) {
                continue;
            } else if(!dayOfWeek.equals(s.getDayOfWeek()) && !s.isPassed()) {
                 // if the isPassed field is false or null, wait until the current days of week same day and same time then set to true 
                // and delete the schedule
                // check if the isPassed field is true, delete the schedule
                // set isPassed to true and delete the schedule in database and update in cache
                s.setPassed(true);
                restApiService.deleteRequest(_DELETE_SCHEDULE_API + s.getSchedule_id().toString(), null);

            } else if(dayOfWeek.equals(s.getDayOfWeek()) && currentTime.equals(s.getScheduleTime().minusMinutes(30))) {
                // send email to user
                restApiService.postRequest(_SEND_REMIND_API, s, Void.class);
            } else if(dayOfWeek.equals(s.getDayOfWeek()) && currentTime.isAfter(s.getScheduleTime())) {
                // if the current days of week is the same day of week with schedule and time is above  30mins, send email (2)
                // set isPassed to true and delete the schedule in database and update in cache 
                restApiService.deleteRequest(_DELETE_SCHEDULE_API + s.getSchedule_id().toString(), null);
            } else {
                // throw new Exception("Error of conditional");
            }
        }
        

    }
}
