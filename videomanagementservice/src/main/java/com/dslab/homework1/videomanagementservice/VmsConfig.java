package com.dslab.homework1.videomanagementservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class VmsConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    VmsUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                // 1. Post /videos
                .antMatchers(HttpMethod.POST, "/videos").hasAuthority("USER")

                // 2. Post /videos/:id
                .antMatchers(HttpMethod.POST, "/videos/{id}").hasAuthority("USER")

                // 3. Get /videos
                .antMatchers(HttpMethod.GET, "/videos").permitAll()

                // 4. Get /videos/:id
                .antMatchers(HttpMethod.GET, "/videos/{id}").permitAll()

                // 5. Post /register
                .antMatchers("/register").permitAll()


                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}