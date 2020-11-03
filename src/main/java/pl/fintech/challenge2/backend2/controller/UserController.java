package pl.fintech.challenge2.backend2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fintech.challenge2.backend2.controller.dto.RegistrationDTO;
import pl.fintech.challenge2.backend2.controller.mapper.UserMapper;
import pl.fintech.challenge2.backend2.domain.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    //todo: powinienem takie rzeczy obslugiwać za pomocą aspektów, na razie zrob happy path
//    private final MapValidationErrorService mapValidationErrorService;

    private final UserService userService;

    private final UserMapper userMapper;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationDTO userDTO){
        log.info("POST /register, registering user:{}", userDTO);

        return ResponseEntity.status(201).body(userService.saveUser(userMapper.mapRegistrationDTOToObject(userDTO)));
    }
}
