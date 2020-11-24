package pl.fintech.challenge2.backend2.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.fintech.challenge2.backend2.controller.dto.ChangeEmailDTO;
import pl.fintech.challenge2.backend2.controller.dto.ChangePasswordDTO;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {
        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            Set<Role> roles = new HashSet<>();
            for(Role role : user.getRoles()){
                roles.add(roleRepository.findByName(role.getName()).orElseGet(()->roleRepository
                        .save(new Role(role.getName()))));
            }
            user.setRoles(roles);
            return userRepository.save(user);
        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+user.getUsername()+"' already exists");
        }
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User changeEmail(Long id, ChangeEmailDTO changeEmailDTO) {
        User user = findById(id);
        if(bCryptPasswordEncoder.encode(changeEmailDTO.getPassword()).equals(user.getPassword())){
            user.setEmail(changeEmailDTO.getNewEmail());
        }
        return userRepository.save(user);
    }

    @Override
    public User changePassword(Long id, ChangePasswordDTO changePasswordDTO) {
        User user = findById(id);
        if(bCryptPasswordEncoder.encode(changePasswordDTO.getOldPassword()).equals(user.getPassword())){
            user.setPassword(changePasswordDTO.getNewPassword());
        }
        return userRepository.save(user);
    }
}
