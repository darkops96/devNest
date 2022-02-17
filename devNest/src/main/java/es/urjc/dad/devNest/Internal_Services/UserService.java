package es.urjc.dad.devNest.Internal_Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Repositories.UserRepository;
import es.urjc.dad.devNest.Model.User;

@Component
@SessionScope
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private User myUser;

    public boolean login(String username, String password)
    {
        User u = new User(username);
        UserEntity uE = u.getUserEntity();

        if(uE.getPassword().equals(password))
        {
            myUser = u;
            return true;
        }
        else
        {
            myUser = null;
            return false;
        }
    }

    public boolean register(String username, String password, String email)
    {
        myUser = new User(username, password, email);
        myUser.saveUser();
        return true;     
    }

    public void logout()
    {
        myUser = null;
    }

    public List<UserEntity> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getMyUser()
    {
        return myUser;
    }
}
