package vn.edu.iuh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.authen.UserPrincipal;
import vn.edu.iuh.entity.Role;
import vn.edu.iuh.entity.Token;
import vn.edu.iuh.entity.User;
import vn.edu.iuh.service.RoleService;
import vn.edu.iuh.service.TokenService;
import vn.edu.iuh.service.UserService;
import vn.edu.iuh.util.JwtTokenUtil;

import java.util.HashSet;
import java.util.Set;

@RestController
public class AuthController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/register")
    public User register(@RequestBody  User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findById(13L));
        user.setRoles(roles);
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        System.out.println("toi day");
        UserPrincipal userPrincipal = userService.findByUserName(user.getUsername());
        System.out.println("toi day 2");
        if (user == null || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account or password is not valid!");
        }
        Token token = new Token();
        token.setToken(jwtTokenUtil.generateToken(userPrincipal));
        token.setExpDate(jwtTokenUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        tokenService.createToken(token);
        return ResponseEntity.ok(token.getToken());
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('ROLE_USER_READ')")
    public ResponseEntity hello() {
        return ResponseEntity.ok("hello word!");
    }
}
