package food.delivery.minh.common.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Integer schedule_id;
    private DayOfWeek dayOfWeek;
    private LocalTime scheduleTime;
    private String name;
    private String category;
    private boolean isPassed;
    private List<Integer> accountIds;
    private List<UUID> productId;
}
