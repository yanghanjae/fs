package yhj.fs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity 
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .logout(logout -> logout 
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
            )
            .authorizeHttpRequests(authorize -> authorize 
                .anyRequest().permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable)
             
            
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable());

        return http.build();
    }

    
}