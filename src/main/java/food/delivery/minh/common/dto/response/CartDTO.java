package food.delivery.minh.common.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private int cartId;

    private double price;

    private List<ProductDTO> products = new ArrayList<>();
}
