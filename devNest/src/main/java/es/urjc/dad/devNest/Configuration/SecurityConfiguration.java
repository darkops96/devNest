package es.urjc.dad.devNest.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {        
        http.authorizeRequests().antMatchers("/css/**").permitAll();

        http.authorizeRequests().antMatchers("/").permitAll();        
        http.authorizeRequests().antMatchers("/profile/**").permitAll();
        http.authorizeRequests().antMatchers("/{id}/image").permitAll();
        
        http.authorizeRequests().antMatchers("/login").anonymous();
        http.authorizeRequests().antMatchers("/register").anonymous();
        http.authorizeRequests().antMatchers("/loginerror").anonymous();
        http.authorizeRequests().antMatchers("/registererror").anonymous();

        http.authorizeRequests().anyRequest().authenticated();        
    }
}
