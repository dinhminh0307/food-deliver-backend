package food.delivery.minh.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private int account_id;
    
    private String email;

    private String lastName;

    private String firstName;

    private String phoneNumber;

    private String dob;

    private String imageUrl;
}
