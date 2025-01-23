package food.delivery.minh.common.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import food.delivery.minh.common.models.accounts.Account;
import food.delivery.minh.modules.users.repos.UserRepository;

@Component
public class AppUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // First, try to find the user as a Farmer
        Account user = userRepository.findByEmail(email).orElseThrow();
        if (user != null) {
            System.out.println("User detail error");
            return mapToUserDetails((Account) user);
        }

        
        // If not found in either, throw an exception
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    // Helper method to convert Farmer/Receptionist to UserDetails
    private UserDetails mapToUserDetails(Account user) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
            .password(user.getPassword())
            .build();
    }
}