package es.urjc.dad.devNest.Configuration;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.urjc.dad.devNest.Internal_Services.User_Services.RepositoryUserDetailsService;

/**
 * Class in charge of spring security
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    public RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12, new SecureRandom());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    /**
     * sets the all the URLS all users have access to, the private URLS only own users can access to and the URLs only the admin
     * has access to
     * Also sets in spring security the URLS for the different actions in the login and logout
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {       
        // Set public URLs
        http.authorizeRequests().antMatchers("/css/**").permitAll();

        http.authorizeRequests().antMatchers("/").permitAll(); 
        http.authorizeRequests().antMatchers("/game/*").permitAll();  
        http.authorizeRequests().antMatchers("/gamejam/*").permitAll();  
              
        http.authorizeRequests().antMatchers("/login").permitAll();     
        http.authorizeRequests().antMatchers("/login-error").permitAll();     
        http.authorizeRequests().antMatchers("/register").permitAll();
        http.authorizeRequests().antMatchers("/registerUser").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();         
        http.authorizeRequests().antMatchers("/profile/*").permitAll();
        http.authorizeRequests().antMatchers("/*/image").permitAll();  
        http.authorizeRequests().antMatchers("/game/{gId}/download-game").permitAll();

        // Set private admin URLs
        http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

        // Set private user URLs
        http.authorizeRequests().anyRequest().hasRole("USER"); 
        
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
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
