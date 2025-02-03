package food.delivery.minh.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends AccountDTO {
    private CartDTO cart; // Contains cart details instead of just cartId

    public UserDTO(int accountId, String email, String lastName, String firstName, 
                   String phoneNumber, String dob, String imageUrl, CartDTO cart) {
        super(accountId, email, lastName, firstName, phoneNumber, dob, imageUrl);
        this.cart = cart;
    }
}