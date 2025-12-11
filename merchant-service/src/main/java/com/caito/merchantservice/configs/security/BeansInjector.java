package com.caito.merchantservice.configs.security;

import com.caito.merchantservice.persistence.repositories.MerchantUserRepository;
import com.pp.commonsservice.exceptions.NotFoundException;
import com.pp.commonsservice.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BeansInjector {
    private final MerchantUserRepository userRepository;

    /**
     * Provides a PasswordEncoder bean using BCrypt hashing algorithm.
     *
     * @return a PasswordEncoder instance
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the AuthenticationProvider using a DaoAuthenticationProvider.
     *
     * @return the configured AuthenticationProvider
     */
    @Bean
    UserDetailsService userDetailsService() {
        return user -> userRepository.findByEmail(user)
                .orElseThrow(() -> {
                    log.warn(WriteLog.logWarning("User not found"));
                    return new NotFoundException("User not found");
                });
    }


    /**
     * Provides the AuthenticationManager bean.
     *
     * @param configuration the AuthenticationConfiguration
     * @return the AuthenticationManager instance
     * @throws Exception if an error occurs while retrieving the AuthenticationManager
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception {
        return configuration.getAuthenticationManager();
    }
}
