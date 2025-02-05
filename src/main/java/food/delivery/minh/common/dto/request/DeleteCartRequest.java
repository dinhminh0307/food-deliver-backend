package food.delivery.minh.common.dto.request;

import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.common.models.products.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCartRequest {
    private Cart cart;
    private User user;

}
