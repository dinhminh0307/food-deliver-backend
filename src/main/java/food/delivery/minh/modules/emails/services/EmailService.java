package food.delivery.minh.modules.emails.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import food.delivery.minh.common.api.RestApiService;
import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.common.models.products.Cart;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    RestApiService restApiService;

    private static final  String CART_FIND_ID_API = "http://localhost:8080/cart/id?cartId=";

    private static final String GET_USER_URL = "http://localhost:8080/currentUser";

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fromemail@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);

    }

    public void sendAddToCartConfirm(int cartId) {
        // get cart detail
        Cart cart = new Cart();
        ResponseEntity<Cart> response  = restApiService.getRequest(CART_FIND_ID_API + cartId, Cart.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            cart = response.getBody();
        }
         // Construct the email content
         String emailSubject = "Cart Confirmation - Cart ID: " + cart.getCartId();
         StringBuilder emailContent = new StringBuilder();
        
         // get user detail
         User user = restApiService.getRequest(GET_USER_URL, User.class).getBody();

         emailContent.append("Dear ")
                     .append(user.getFirstName())
                     .append(",\n\n")
                     .append("We are pleased to inform you that your order has been confirmed. Here are the details of your order:\n\n")
                     .append("Cart ID: ").append(cart.getCartId()).append("\n")
                     .append("Total Cost: $").append(String.format("%.2f", cart.getPrice())).append("\n\n")
                     .append("Thank you for choosing our service!\n\n")
                     .append("Best regards,\n")
                     .append("Hover Sprite");
        
        // send email to smtp server
        sendSimpleEmail(user.getEmail(), emailSubject, emailContent.toString());
    }
}
