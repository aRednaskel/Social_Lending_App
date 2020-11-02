package pl.fintech.challenge2.backend2.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fintech.challenge2.backend2.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository employeeUserRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return employeeUserRepository.findByUsername(username)
                .map(e -> new User(username, e.getPassword(), e.getAuthorities()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}