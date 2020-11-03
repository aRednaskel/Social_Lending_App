package pl.fintech.challenge2.backend2.controller.dto;

import lombok.Data;
import pl.fintech.challenge2.backend2.domain.user.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RegistrationDTO {
    @Email(message = "Wprowadź poprawny email")
    private String email;

    @Size(min = 6, message = "Hasło musi mieć conajmniej 6 znaków")
    private String password;

    private String name;

    private String surname;

    private String phone;

    private List<Role> roles;
}
