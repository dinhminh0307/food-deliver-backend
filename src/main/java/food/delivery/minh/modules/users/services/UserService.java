    package food.delivery.minh.modules.users.services;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import food.delivery.minh.common.auth.jwt.JwtRequestFilter;
    import food.delivery.minh.common.models.accounts.User;
    import food.delivery.minh.modules.users.repos.UserRepository;

    @Service
    public class UserService {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private JwtRequestFilter authFilter;

        public User createAccount(User user) {
            // encrypt the user password
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);

            return userRepository.save((User) user); 
        }

        public User findByEmail(String email) {
            return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

        public User getCurrentUser() {
            return findByEmail(authFilter.getUserEmail());
        }

        public User updateCurrentUser(User user) {
             // Save and return the updated user
            return userRepository.save(user); 
        }

        public Page<User> getAllUser(Pageable pageable) {
            return userRepository.findAll(pageable);
        }
    }
