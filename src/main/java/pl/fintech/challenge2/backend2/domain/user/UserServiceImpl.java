package pl.fintech.challenge2.backend2.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.fintech.challenge2.backend2.domain.bank.BankAppClient;

import java.util.HashSet;
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
            user.setAccountNumber(bankAppClient
                    .createAccount(user.getEmail()));
            return userRepository.save(user);
        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+user.getUsername()+"' already exists");
        }
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
}
