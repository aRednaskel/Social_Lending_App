package pl.fintech.challenge2.backend2.domain.user;

import pl.fintech.challenge2.backend2.controller.dto.ChangeEmailDTO;
import pl.fintech.challenge2.backend2.controller.dto.ChangePasswordDTO;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User findById(Long id);
    void removeById(Long id);

    User changePassword(Long id, ChangePasswordDTO changePasswordDTO);

    User changeEmail(Long id, ChangeEmailDTO changeEmailDTO);

    Optional<User> findByEmail(String name);
}
