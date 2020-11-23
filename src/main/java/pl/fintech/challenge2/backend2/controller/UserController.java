package pl.fintech.challenge2.backend2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity getUsersData(@PathVariable Long id) {
        log.info("GET /api/users/{}", id);

        return ResponseEntity.status(200).body(userService.findById(id));
    }

    @PreAuthorize("permitAll()")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("DELETE /api/users/{}", id);

        userService.removeById(id);
    }
}
