package food.delivery.minh.modules.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.modules.users.repos.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createAccount(User user) {
        System.out.println("User login: " + user.getEmail());
        return userRepository.save((User) user); 
    }
}
