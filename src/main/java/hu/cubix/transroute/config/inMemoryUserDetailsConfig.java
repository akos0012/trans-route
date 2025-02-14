package hu.cubix.transroute.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class inMemoryUserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails addressManager = User.builder()
                .username("address_manager")
                .password(passwordEncoder.encode("pass"))
                .authorities("AddressManager")
                .build();

        UserDetails transportManager = User.builder()
                .username("transport_manager")
                .password(passwordEncoder.encode("pass"))
                .authorities("TransportManager")
                .build();

        return new InMemoryUserDetailsManager(addressManager, transportManager);
    }
}
