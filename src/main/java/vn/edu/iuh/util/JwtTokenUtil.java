package vn.edu.iuh.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vn.edu.iuh.authen.UserPrincipal;

@Component
public class JwtTokenUtil {
    private static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String USER = "";
    private static final String SECRET = "";

    public String generateToken(UserPrincipal user) {
        String token = null;


        return token;
    }
}
