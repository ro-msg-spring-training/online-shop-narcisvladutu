package ro.msg.learning.shop.configuration.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import ro.msg.learning.shop.exception.entity_exception.CustomerException;
import ro.msg.learning.shop.model.Credential;
import ro.msg.learning.shop.service.security_utils.UserDetailsService;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) {
        final List<Credential> allCustomerCredentials = userDetailsService.getUsernamesAndPasswords();

        allCustomerCredentials.forEach(credentials -> {
            try {
                auth.inMemoryAuthentication().withUser(credentials.getUsername())
                        .password(passwordEncoder().encode(credentials.getPassword())).authorities("ROLE_USER");
            } catch (Exception e) {
                throw new CustomerException(e.getMessage());
            }
        });
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/customers/")
                .permitAll()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);
        http
                .addFilterAfter(new CustomFilter(),
                        BasicAuthenticationFilter.class);
        //postman set-ups
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}