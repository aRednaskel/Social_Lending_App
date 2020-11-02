package pl.fintech.challenge2.backend2.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer")){
            chain.doFilter(request, response);
            return;
        }
        Claims claims = Jwts.parser().setSigningKey("SECRET")
                .parseClaimsJws(token.replace("Bearer ", "")).getBody();

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        String authorities = claims.get("authorities", String.class);

        if(authorities != null){
            grantedAuthorities = Arrays.stream(authorities.split(",")).map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        String username = claims.getSubject();
        if(username != null){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            chain.doFilter(request, response);
        }else{
            response.setStatus(403);
        }
    }
}
