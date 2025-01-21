package food.delivery.minh.common.models.accounts;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name="admins", schema = "food-product")
@Entity
public class Admin extends Account{
    
}
