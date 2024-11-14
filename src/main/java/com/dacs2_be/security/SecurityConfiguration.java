package com.dacs2_be.security;

import com.dacs2_be.service.impl.UserDetailsImpl;
import com.dacs2_be.service.impl.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableScheduling
public class SecurityConfiguration {


    private final UserDetailsImpl userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/", "/index", "/home", "/auth/**", "/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/user/signin")
                        .loginProcessingUrl("/authenticateTheUser")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/user/signin?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/user/signin")
                        .permitAll()
                )
                .httpBasic();

        return http.build();
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable();
////        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
//        http.authorizeRequests()
//                .requestMatchers("/**", "/auth/**", "/api/**", "/api/**/**").permitAll()
//                .anyRequest().authenticated();
//        http.httpBasic();
//        http.logout().invalidateHttpSession(true).clearAuthentication(true);
//        http.headers().frameOptions().sameOrigin();
//        http.exceptionHandling();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

}
