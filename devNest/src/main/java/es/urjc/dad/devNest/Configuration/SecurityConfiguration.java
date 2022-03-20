package es.urjc.dad.devNest.Configuration;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.urjc.dad.devNest.Internal_Services.UserRepositoryAuthenticationProvider;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    public UserRepositoryAuthenticationProvider authenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {    
        http.authorizeRequests().antMatchers("/css/**").permitAll();

        http.authorizeRequests().antMatchers("/").permitAll();        
        http.authorizeRequests().antMatchers("/profile/*").permitAll();
        http.authorizeRequests().antMatchers("/*/image").permitAll();        
        http.authorizeRequests().antMatchers("/login").permitAll();     
        http.authorizeRequests().antMatchers("/login-error").permitAll();     
        http.authorizeRequests().antMatchers("/register").permitAll();
        http.authorizeRequests().antMatchers("/register-error").permitAll();

        http.authorizeRequests().anyRequest().authenticated(); 
        
        // Log in form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("psw");
        http.formLogin().defaultSuccessUrl("/");
        http.formLogin().failureUrl("/login-error");

        // Log out
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(authenticationProvider);
    }
}
