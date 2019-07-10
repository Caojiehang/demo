package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
* @Author: Jiehang CAO
* @Description: this is security configuration
* @Date: 18:09 2019-06-30
*/
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserService myUserService;
    /**
     * the usage of passwordEncoder is required
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("Admin")
                .password(new BCryptPasswordEncoder().encode("123456")).roles("Manager");
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("jason")
                .password(new BCryptPasswordEncoder().encode("7654321")).roles("Admin");
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("demo")
                .password(new BCryptPasswordEncoder().encode("demo")).roles("User");
        auth.userDetailsService(myUserService);

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll().and().
                formLogin();

        http.csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/image/**");
    }

}
