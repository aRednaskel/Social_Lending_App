package pl.fintech.challenge2.backend2.controller.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class ChangeEmailDTO {
    private String password;

    @Email(message = "Wprowadź poprawny email")
    private String newEmail;
}
