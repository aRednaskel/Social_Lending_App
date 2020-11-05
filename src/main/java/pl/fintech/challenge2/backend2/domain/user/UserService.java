package pl.fintech.challenge2.backend2.domain.user;

public interface UserService {
    User saveUser(User user);
    User findById(Long id);
}
