package com.sbs.sbsgroup7.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("bankUserDetailsService")
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/accounts")
                    .hasAnyAuthority("ADMIN", "TIER1", "TIER2")
                .antMatchers("/home")
                    .hasAnyAuthority("ADMIN", "TIER1", "TIER2", "USER", "MERCHANT")
                .antMatchers("/").permitAll()
                .antMatchers("/user/**")
                    .hasAnyAuthority("USER")
                .antMatchers("/merchant/**")
                    .hasAnyAuthority("MERCHANT")
                .antMatchers("/tier1/**")
                    .hasAnyAuthority("TIER1")
                .antMatchers("/tier2/**")
                    .hasAnyAuthority("TIER2")
                .antMatchers("/admin/**")
                    .hasAnyAuthority("ADMIN")
                .and()
                    .formLogin()
                        .loginPage("/login")  //Loginform all can access ..
                        .successHandler(myAuthenticationSuccessHandler())
//                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error")
                        .permitAll()
                .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .and()
                    .csrf()
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                    .headers()
                        .frameOptions()
                        .and()
                        .defaultsDisabled()
                        .xssProtection();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new UrlAuthenticationSuccessHandler();
    }
}