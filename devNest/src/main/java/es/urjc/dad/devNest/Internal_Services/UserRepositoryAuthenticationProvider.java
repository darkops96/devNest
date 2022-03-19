package es.urjc.dad.devNest.Internal_Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Repositories.UserRepository;

@Component
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        Optional<UserEntity> user = userRepository.findByAlias(authentication.getName());
        if(!user.isPresent())
        {
            throw new BadCredentialsException("User not found");
        }

        UserEntity u = user.get();
        String password = (String) authentication.getCredentials();
        if (new BCryptPasswordEncoder(10).matches(password, u.getPassword()))
        {
            throw new BadCredentialsException("Wrong password");
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        for (String role : u.getRoles())
        {
            roles.add(new SimpleGrantedAuthority(role));
        }
        
        return new UsernamePasswordAuthenticationToken(u.getAlias(), password, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return false;
    }    
}
