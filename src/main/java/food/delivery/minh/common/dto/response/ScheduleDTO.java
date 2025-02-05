package food.delivery.minh.common.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Integer schedule_id;

    private DayOfWeek dayOfWeek; // Enum type (Monday, Tuesday, etc.)

    private LocalTime scheduleTime; // Rename to avoid conflicts
    
    private String name;

    private String category;

    private List<UserDTO> users;

    private List<ProductDTO> products = new ArrayList<>();
}
