package es.urjc.dad.devNest.Internal_Services;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Repositories.UserRepository;

@Component
@SessionScope
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private UserEntity myUser;

    //region INIT
    @PostConstruct
    private void addAdmin()
    {
        UserEntity ue = new UserEntity("admin", "admin", "admin@devnest.es");
        Optional<UserEntity> u = userRepository.findByAlias(ue.getAlias());

        if(!u.isPresent())
        {
            userRepository.save(ue);
        }        
    }
    //endregion


    public boolean login(String username, String password)
    {
        Optional<UserEntity> u = userRepository.findByAlias(username);

        if(u.isPresent())
        {
            if(u.get().getPassword().equals(password))
            {
                myUser = u.get();
                return true;
            }
            else
            {
                myUser = null;
                return false;
            }
        }
        else
        {
            return false;
        }       
    }

    public boolean register(String username, String password, String email)
    {
        Optional<UserEntity> u = userRepository.findByAlias(username);

        if(!u.isPresent())
        {
            myUser = new UserEntity(username, password, email);
            userRepository.save(myUser);
            return true; 
        }
        else
        {
            myUser = null;
            return false;
        }            
    }

    public void logout()
    {
        myUser = null;
    }

    public List<UserEntity> getAllUsers()
    {
        return userRepository.findAll();
    }

    public UserEntity getMyUser()
    {
        return myUser;
    }
}
