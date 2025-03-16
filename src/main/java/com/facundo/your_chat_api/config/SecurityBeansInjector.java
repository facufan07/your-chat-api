package com.facundo.your_chat_api.config;

import com.facundo.your_chat_api.exception.ObjectNotFoundException;
import com.facundo.your_chat_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder(this.passwordEncoder());
        authenticationStrategy.setUserDetailsService(this.userDetailsService());

        return authenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (email) -> {
            return this.userRepository.findByMail(email)
                    .orElseThrow(() -> new ObjectNotFoundException("User not found with email: " + email));
        };
    }
}
