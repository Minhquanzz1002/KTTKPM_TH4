package vn.edu.iuh.filter;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.edu.iuh.authen.UserPrincipal;
import vn.edu.iuh.entity.Token;
import vn.edu.iuh.service.TokenService;
import vn.edu.iuh.util.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        UserPrincipal user = null;
        Token token = null;

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Token")) {
            String jwt = authorizationHeader.substring(6);
            user = jwtTokenUtil.getUserFromToken(jwt);
            token = tokenService.findByToken(jwt);
        }

        if (user != null && token != null && token.getExpDate().after(new Date())) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            user.getAuthorities().forEach(a -> authorities.add(new SimpleGrantedAuthority((String) a)));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
