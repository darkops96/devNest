package es.urjc.dad.devNest.Model;


import org.springframework.beans.factory.annotation.Autowired;

import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Repositories.UserRepository;

public class User {

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    //Register constructor
    public User(String alias, String password, String email)
    {
        user = new UserEntity(alias, password, email);
    }

    //Log in constructor
    public User(String alias)
    {
        user = userRepository.findByAlias(alias).get(0);
    }

    //Save or update user
    public void saveUser()
    {
        userRepository.save(user);
    }

    //Getter
    public UserEntity getUserEntity()
    {
        return user;
    }
}
