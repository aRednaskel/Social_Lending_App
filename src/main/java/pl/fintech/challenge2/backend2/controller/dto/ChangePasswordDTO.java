package pl.fintech.challenge2.backend2.controller.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ChangePasswordDTO {
    private String oldPassword;

    @Size(min = 6, message = "Hasło musi mieć conajmniej 6 znaków")
    private String newPassword;
}
