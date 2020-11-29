package pl.fintech.challenge2.backend2.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import pl.fintech.challenge2.backend2.domain.user.User;
import pl.fintech.challenge2.backend2.domain.user.UserRepository;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SecurityExpressions {

    private final UserRepository userRepository;

    @Transactional
    public boolean hasPersonId(Authentication authentication, Long personId) {
        String username = String.valueOf(authentication.getPrincipal());
        if (userRepository.findByEmail(username).isPresent()){
            Optional<User> userOptional = userRepository.findByEmail(username);
            Long userPersonId = null;
            if(userOptional.isPresent()){
                userPersonId = userOptional.get().getId();
            }
            return personId.equals(userPersonId);
        }else{
            return false;
        }
    }
}
