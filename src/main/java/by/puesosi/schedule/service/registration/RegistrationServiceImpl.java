package by.puesosi.schedule.service.registration;

import by.puesosi.schedule.dao.UserRepository;
import by.puesosi.schedule.entity.Role;
import by.puesosi.schedule.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * The type Registration service.
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Instantiates a new Registration service.
     *
     * @param userRepository        the user repository
     * @param bCryptPasswordEncoder the b crypt password encoder
     */
    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String checkNewUserData(User user) {
        User userByEmail = userRepository.findFirstByEmail(user.getEmail());
        User userByUsername = userRepository.findFirstByUsername(user.getUsername());
        if((userByEmail != null && userByEmail.getPassword() != null) && userByUsername != null){
            return "usernameAndEmailError";
        }
        if(userByEmail != null && userByEmail.getPassword() != null){
            return "emailError";
        }
        if (userByUsername != null){
            return "usernameError";
        }
        if(userByEmail != null && userByEmail.getPassword() == null){
            userByEmail.setFirstName(user.getFirstName());
            userByEmail.setLastName(user.getLastName());
            userByEmail.setUsername(user.getUsername());
            userByEmail.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(userByEmail);
            return "success";
        }

        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "success";
    }
}
