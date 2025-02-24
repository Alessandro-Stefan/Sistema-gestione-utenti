package com.example.apiUsers.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//classe che definisce le regole di sicurezza per l'applicazione
//annotazione che indica a Spring che questa è una classe di configurazione
@Configuration
//abilita la sicurezza web di Spring Security
@EnableWebSecurity
public class SecurityConfig {
    //annotazione che permette a Spring di gestire questo componente
    @Bean
    //metodo che include due regole:
    //1.Permette a tutti di accedere all'URL "/login" senza un'autenticazione
    //2.Richiede l'autenticazione per qualsiasi altra richiesta
    //filtro di sicurezza
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
      http
              .authorizeHttpRequests((authorize) -> authorize
                     // .requestMatchers("/login").permitAll()
                      .anyRequest().authenticated()
              )
              .httpBasic(Customizer.withDefaults())
              .formLogin(Customizer.withDefaults())
              .csrf((csrf) -> csrf.disable());

      return http.build();
    }

   @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return  new ProviderManager(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails userDetails = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
