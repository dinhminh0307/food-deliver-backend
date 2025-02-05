package food.delivery.minh.common.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private int schedule_id;

    private String time;

    private String name;

    private String category;

    private UserDTO owner;

    private List<ProductDTO> products = new ArrayList<>();
}
