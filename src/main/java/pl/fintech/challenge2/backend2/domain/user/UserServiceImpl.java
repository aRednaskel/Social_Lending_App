package pl.fintech.challenge2.backend2.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.fintech.challenge2.backend2.controller.dto.ChangeEmailDTO;
import pl.fintech.challenge2.backend2.controller.dto.ChangePasswordDTO;
import pl.fintech.challenge2.backend2.domain.bank.BankAppClient;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final BankAppClient bankAppClient;

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
            user.setAccountNumber(bankAppClient.createAccount(user.getEmail()));
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
        if(bCryptPasswordEncoder.matches(changeEmailDTO.getPassword(), user.getPassword())){
            user.setEmail(changeEmailDTO.getNewEmail());
        }
        return userRepository.save(user);
    }

    @Override
    public User changePassword(Long id, ChangePasswordDTO changePasswordDTO) {
        User user = findById(id);
        if(bCryptPasswordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(changePasswordDTO.getNewPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String name) {
        return userRepository.findByEmail(name);
    }

    @Override
    public Long getCurrentUserId() throws UsernameNotFoundException{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if(user != null){
            return user.getId();
        }else{
            throw new UsernameNotFoundException("User wasn't found during getting his id");
        }
    }

    @Override
    public User getCurrentUser() throws UsernameNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }
}
