package pl.fintech.challenge2.backend2.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.fintech.challenge2.backend2.domain.user.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        setAuthenticationManager(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        Claims claims = new DefaultClaims();
        claims.put("authorities", authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(",")));
        claims.put("user-id", userRepository.findByEmail(authResult.getName()).get().getId());
        claims.setSubject(authResult.getName());
        claims.setExpiration(new Date(System.currentTimeMillis() + 1000*3600*24));
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "SECRET")
                .compact();
        response.setHeader("X-Auth", "Bearer " + token);
    }
}
