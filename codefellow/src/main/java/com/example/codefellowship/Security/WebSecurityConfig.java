package com.example.codefellowship.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        return encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());

    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login*").permitAll() // anyone can access
                .antMatchers("/signup*").permitAll() // anyone can access
                .antMatchers("/").permitAll() // anyone can access
                .antMatchers("/style.css").permitAll()// anyone can access
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login") // process the login after submit
                .defaultSuccessUrl("/myprofile") // success login go to route /hello
                .failureUrl("/login") // Failure login go back to log in
                .and()
                .logout()
                .logoutUrl("/perform_logout") //  process the logout after submit
                .logoutSuccessUrl("/login") // success logout go back to log in
                .deleteCookies("JSESSIONID");
    }
}
