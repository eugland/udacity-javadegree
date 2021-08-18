package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // maybe remove the super class caller?
        auth.authenticationProvider(this.authenticationService);
        auth.inMemoryAuthentication()
                .withUser("user1").password("{noop}pass").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: remove the following 3 lines, they are here for H2-console development purpose
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers("/signup", "/login", "/css/**", "/h2-console/**", "/js/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login");

        http.formLogin()
                .defaultSuccessUrl("/home", true);
    }
}
