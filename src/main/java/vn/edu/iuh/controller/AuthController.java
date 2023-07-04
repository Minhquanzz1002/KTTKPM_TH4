package vn.edu.iuh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.authen.UserPrincipal;
import vn.edu.iuh.entity.Token;
import vn.edu.iuh.entity.User;
import vn.edu.iuh.service.TokenService;
import vn.edu.iuh.service.UserService;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public User register(@RequestBody  User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        UserPrincipal userPrincipal = userService.findByUserName(user.getUsername());
        if (user == null || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account or password is not valid!");
        }
        Token token = new Token();
//        token.setToken();
//        token.setExpDate();
        token.setCreatedBy(user.getId());
        tokenService.createToken(token);
        return ResponseEntity.ok(token.getToken());
    }

    @GetMapping("/hello")
    public ResponseEntity hello() {
        return ResponseEntity.ok("hello word!");
    }
}