package co.zw.santech.onlinepayments.services;

import co.zw.santech.onlinepayments.models.CustomUserDetail;
import co.zw.santech.onlinepayments.models.User;
import co.zw.santech.onlinepayments.repositories.RoleRepository;
import co.zw.santech.onlinepayments.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    CustomerUserDetailService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Long countUsers() {
        return this.userRepository.countUsersByRolesEquals(this.roleRepository.findById(2).get());
    }

    public Page<User> findAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.userRepository.findAllByRolesEquals(this.roleRepository.findById(1).get(), pageable);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findUserByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return user.map(CustomUserDetail::new).get();
    }
}
