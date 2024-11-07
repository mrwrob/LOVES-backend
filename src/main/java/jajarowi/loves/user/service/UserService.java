package jajarowi.loves.user.service;

import jajarowi.loves.assignee.entity.Assignee;
import jajarowi.loves.scientist.entity.Scientist;
import jajarowi.loves.user.entity.User;
import jajarowi.loves.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static jajarowi.loves.security.Role.*;

@Service
@Slf4j
public class UserService  implements UserDetailsService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository repository, PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByLogin(login);
        if (foundUser.isEmpty()) {
            log.error("User: {} not found in database", login);
            throw new UsernameNotFoundException("User with login + " + login + " not found");
        }
        User user = foundUser.get();

        String role;
        if (user instanceof Scientist) role = ROLE_SCIENTIST.name();
        else if (user instanceof Assignee) role = ROLE_ASSIGNEE.name();
        else role = ROLE_USER.name();

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }

    public boolean hasUserId(Authentication authentication, Long userId){
        String login = authentication.getName();
        Optional<User> userOptional= userRepository.findByLogin(login);
        return authentication.isAuthenticated() && userOptional.isPresent() && Objects.equals(userOptional.get().getId(), userId);
    }

    public boolean hasUserLogin(Authentication authentication, String username){
        Optional<User> userOptional= userRepository.findByLogin(authentication.getName());
        return authentication.isAuthenticated() && userOptional.isPresent() && Objects.equals(userOptional.get().getLogin(), username);
    }

    @Transactional
    public void updateUsername(User user, String newLogin) {
        user.setLogin(newLogin);
        userRepository.save(user);
    }

    @Transactional
    public User updatePassword(User user, String oldPassword, String newPassword) {
        if(passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        }
        return null;
    }

    public Optional<User> find(String login) {
        return userRepository.findByLogin(login);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
