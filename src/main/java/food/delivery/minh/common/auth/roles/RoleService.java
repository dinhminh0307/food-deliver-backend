package food.delivery.minh.common.auth.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import food.delivery.minh.common.enums.RoleEnum.ROLE;
import food.delivery.minh.common.models.accounts.Admin;
import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.modules.admins.repos.AdminRepository;
import food.delivery.minh.modules.users.repos.UserRepository;

@Service
public class RoleService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    public ROLE checkRole(String email) {
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if(admin != null) {
            return ROLE.ADMIN;
        }
        User user = userRepository.findByEmail(email).orElse(null);
        if(user != null) {
            return ROLE.USER;
        }
        throw new UsernameNotFoundException("Can not find user");
    }
}
