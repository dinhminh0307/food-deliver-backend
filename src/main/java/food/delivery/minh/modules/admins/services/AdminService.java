package food.delivery.minh.modules.admins.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import food.delivery.minh.common.models.accounts.Admin;
import food.delivery.minh.modules.admins.repos.AdminRepository;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin createAccount(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save((Admin) admin);
    }

    private Admin findByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
