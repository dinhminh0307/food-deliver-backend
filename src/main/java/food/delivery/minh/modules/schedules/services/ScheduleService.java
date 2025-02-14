package food.delivery.minh.modules.schedules.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import food.delivery.minh.common.api.RestApiService;
import food.delivery.minh.common.dto.request.DeleteCartRequest;
import food.delivery.minh.common.dto.response.CartDTO;
import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.common.models.products.Cart;
import food.delivery.minh.common.models.schedules.Schedule;
import food.delivery.minh.exceptions.PassedException;
import food.delivery.minh.modules.schedules.repos.ScheduleRepository;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    
    @Autowired
    RestApiService restApiService;

    private String GET_USER_URL = "http://localhost:8080/currentUser";

    private static final String DELETE_CART_API_URL = "http://localhost:8080/cart/remove";

    private static final String GET_CURRENT_CART_API = "http://localhost:8080/cart/currentUser";

    public Schedule createSchedule(Schedule schedule) throws NoResourceFoundException, RuntimeException {
        // get current user
        User user = restApiService.getRequest(GET_USER_URL, User.class).getBody();

        // get cart from id
        ResponseEntity<CartDTO> response = restApiService.getRequest(GET_CURRENT_CART_API, CartDTO.class);
        if(response.getBody() == null) {
            throw new NoResourceFoundException(null, "Current user have no cart");
        }
        Cart cart = new Cart();
        cart.setCartId(response.getBody().getCartId());

        // attach to the request
        DeleteCartRequest request = new DeleteCartRequest(cart, user);

        // send delete request to delete the cart
        ResponseEntity<?> res = restApiService.putRequest(DELETE_CART_API_URL, request, Void.class);

        if(res.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error deleting cart: " + res.getStatusCode() + " - " + res.getBody());
        }
        //set the schedule field and the save to database
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getDirectCurrentUserSchedule() throws NoResourceFoundException, PassedException {
        // get current user
        User user = restApiService.getRequest(GET_USER_URL, User.class).getBody();
        List<Schedule> schedules = new ArrayList<>();
        Optional<Schedule> scheduleOptional;
        // handle user does not have schedule case
        if(user.getScheduleIds().isEmpty()) {
            throw new PassedException("user currently has no schedule");
        }
        
        for(int s : user.getScheduleIds()) {
            scheduleOptional = scheduleRepository.findById(s);
            if(!scheduleOptional.isPresent()) {
                throw new NoResourceFoundException(null, "The schedule id is not in database");
            }
            schedules.add(scheduleOptional.get());
        }
        System.out.println("OK");
        return schedules;
    }

    @Cacheable(value = "schedules", key = "'currentUserSchedule'")
    public List<Schedule> getCurrentUserSchedule() throws NoResourceFoundException, PassedException {
        // get current user
        User user = restApiService.getRequest(GET_USER_URL, User.class).getBody();
        List<Schedule> schedules = new ArrayList<>();
        Optional<Schedule> scheduleOptional;
        // handle user does not have schedule case
        if(user.getScheduleIds().isEmpty()) {
            throw new PassedException("user currently has no schedule");
        }
        
        for(int s : user.getScheduleIds()) {
            scheduleOptional = scheduleRepository.findById(s);
            if(!scheduleOptional.isPresent()) {
                throw new NoResourceFoundException(null, "The schedule id is not in database");
            }
            schedules.add(scheduleOptional.get());
        }
        System.out.println("OK");
        return schedules;
    }
}