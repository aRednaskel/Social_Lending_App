package pl.fintech.challenge2.backend2.domain.user;

import pl.fintech.challenge2.backend2.controller.dto.ChangePasswordDTO;

public interface UserService {
    User saveUser(User user);
    User findById(Long id);
    void removeById(Long id);

    User changePassword(Long id, ChangePasswordDTO changePasswordDTO);
}
