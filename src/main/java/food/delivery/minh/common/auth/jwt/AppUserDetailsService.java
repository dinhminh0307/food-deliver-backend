package food.delivery.minh.common.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import food.delivery.minh.common.models.accounts.Account;
import food.delivery.minh.common.models.accounts.Admin;
import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.modules.admins.repos.AdminRepository;
import food.delivery.minh.modules.users.repos.UserRepository;

@Component
public class AppUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // First, try to find the account as user
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return mapToUserDetails((Account) user);
        }

        Admin admin  = adminRepository.findByEmail(email).orElse(null);
        if(admin != null) {
            return mapToUserDetails((Account) admin);
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