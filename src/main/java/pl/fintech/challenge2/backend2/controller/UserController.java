package pl.fintech.challenge2.backend2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fintech.challenge2.backend2.controller.dto.ChangeEmailDTO;
import pl.fintech.challenge2.backend2.controller.dto.ChangePasswordDTO;
import pl.fintech.challenge2.backend2.controller.dto.RegistrationDTO;
import pl.fintech.challenge2.backend2.controller.mapper.UserMapper;
import pl.fintech.challenge2.backend2.domain.user.User;
import pl.fintech.challenge2.backend2.domain.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegistrationDTO userDTO){
        log.info("POST /api/users/register, registering user:{}", userDTO);

        return ResponseEntity.status(201).body(userService.saveUser(userMapper.mapRegistrationDTOToUser(userDTO)));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUsersData(@PathVariable Long id) {
        log.info("GET /api/users/{}", id);

        return ResponseEntity.status(200).body(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityExpressions.hasPersonId(authentication,#id) or hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        log.info("DELETE /api/users/{}", id);

        userService.removeById(id);
    }

    @PutMapping("/{id}/change-email")
    @PreAuthorize("@securityExpressions.hasPersonId(authentication,#id) or hasAuthority('ADMIN')")
    public ResponseEntity<User> changeUserEmail(@PathVariable Long id,
                                @RequestBody ChangeEmailDTO changeEmailDTO) {
        log.info("PUT /api/users/{}/change-email", id);

        return ResponseEntity.status(200).body(userService.changeEmail(id, changeEmailDTO));
    }

    @PutMapping("/{id}/change-password")
    @PreAuthorize("@securityExpressions.hasPersonId(authentication,#id) or hasAuthority('ADMIN')")
    public ResponseEntity<User> changeUserPassword(@PathVariable Long id,
                                                   @RequestBody ChangePasswordDTO changePasswordDTO) {
        log.info("PUT /api/users/{}/change-password", id);

        return ResponseEntity.status(200).body(userService.changePassword(id, changePasswordDTO));
    }
}
