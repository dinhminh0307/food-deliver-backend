package food.delivery.minh.common.models.accounts;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all arguments
public class Account {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "account_seq_gen") // the id of the next records will depend from previous record id
    @SequenceGenerator(
        name = "account_seq_gen", 
        sequenceName = "food-product.account_seq", 
        allocationSize = 1
    )
    private int account_id;
    
    private String email;

    private String lastName;

    private String firstName;

    private String phoneNumber;

    private String dob;

    private String password;

    private String imageUrl;

}
