package vn.edu.iuh.service;

import vn.edu.iuh.authen.UserPrincipal;
import vn.edu.iuh.entity.User;

public interface UserService {
    User createUser(User user);
    UserPrincipal findByUserName(String username);
}
