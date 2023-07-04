package vn.edu.iuh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.authen.UserPrincipal;
import vn.edu.iuh.entity.User;
import vn.edu.iuh.repository.UserRepository;
import vn.edu.iuh.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public UserPrincipal findByUserName(String username) {
        User user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal();
        if (user != null) {
            Set<String> authorities = new HashSet<>();
            if (user.getRoles() != null) {
                user.getRoles().forEach(role -> {
                    authorities.add(role.getKey());
                    role.getPermissions().forEach(p -> authorities.add(p.getKey()));
                });

            }

            userPrincipal.setUserId(user.getId());
            userPrincipal.setUsername(user.getUsername());
            userPrincipal.setPassword(user.getPassword());
            userPrincipal.setAuthorities(authorities);
        }
        return userPrincipal;
    }
}
