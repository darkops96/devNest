package es.urjc.dad.devNest.Internal_Services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Database.Repositories.UserRepository;
import es.urjc.dad.devNest.Database.Repositories.TeamRepository;

@Component
@SessionScope
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;   

    private UserEntity myUser;

    //region INIT
    @PostConstruct
    private void addAdmin()
    {
        userRepository.save(new UserEntity("admin", "admin", "admin@devnest.es"));          
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

    public UserEntity getUser(long id)
    {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent())
            return user.get();
        else
            return null;
    }

    public List<TeamEntity> getUserTeams(long id){
        return teamRepository.findByMembersId(id);
    }

    public List<VideogameEntity> getGames(List<TeamEntity> teams){
        List<VideogameEntity> games = new LinkedList<VideogameEntity>();
        
        for (TeamEntity t : teams) {
            games.add(t.getVideogame());
        }
        return games;
    }
    

}
