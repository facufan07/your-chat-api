package com.facundo.your_chat_api.services.entitiesService.user;

import com.facundo.your_chat_api.dto.RegisterRequest;
import com.facundo.your_chat_api.entities.User;
import com.facundo.your_chat_api.exception.ObjectNotFoundException;
import com.facundo.your_chat_api.exception.PasswordsDoesNotHaveTextException;
import com.facundo.your_chat_api.exception.PasswordsDoesNotMatchException;
import com.facundo.your_chat_api.repositories.UserRepository;
import com.facundo.your_chat_api.utils.Roles;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterRequest registerRequest) {
        this.validatePassword(registerRequest.getPassword(),
                              registerRequest.getRepeatedPassword());

        User user = new User();
        user.setMail(registerRequest.getMail());
        user.setPassword(this.passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreationDate(LocalDateTime.now());
        user.setRole(Roles.ROLE_USER.name());

        return this.userRepository.save(user);
    }

    private void validatePassword(@Size(min = 8) String password, String repeatedPassword) {

        if (!StringUtils.hasText(password) || !StringUtils.hasText(repeatedPassword)) {
            throw new PasswordsDoesNotHaveTextException("Passwords does not have text");
        }

        if (!password.equals(repeatedPassword)) {
            throw new PasswordsDoesNotMatchException("Passwords does not match");
        }
    }

    public User findByMail(String mail) {
        return this.userRepository.findByMail(mail)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with email: " + mail));
    }
}
