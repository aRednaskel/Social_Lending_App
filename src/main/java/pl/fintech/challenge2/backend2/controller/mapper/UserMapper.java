package pl.fintech.challenge2.backend2.controller.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.fintech.challenge2.backend2.controller.dto.RegistrationDTO;
import pl.fintech.challenge2.backend2.domain.user.User;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User mapRegistrationDTOToUser(RegistrationDTO registrationDTO){
        return User.builder()
                .email(registrationDTO.getEmail())
                .password(registrationDTO.getPassword())
                .name(registrationDTO.getName())
                .surname(registrationDTO.getSurname())
                .phone(registrationDTO.getPhone())
                .roles(registrationDTO.getRoles())
                .build();
    }
}
