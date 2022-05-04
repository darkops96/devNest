package es.urjc.dad.devNest.Internal_Services.User_Services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Database.Repositories.UserRepository;
import es.urjc.dad.devNest.Database.Repositories.TeamRepository;

/**
 * Class that provides service to actions related to users
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${security.adminPassword}")
    private String adminPassword;

    //region INIT

    /**
     * Inserts the admin in the database with the roles ADMIN and USER
     */
    @PostConstruct
    private void initDatabase() {
        Optional<UserEntity> u = userRepository.findByAlias("admin");

        if (!u.isPresent()) {
            userRepository.save(new UserEntity("admin", adminPassword, "devnestofficial@gmail.com", "USER", "ADMIN"));
        }
    }
    //endregion

    /**
     * Inserts a user in the database it wasnt register before
     *
     * @param username
     * @param password
     * @param email
     * @return true if the user was registered, false if it already existed
     */
    public boolean register(String username, String password, String email) {
        Optional<UserEntity> u = userRepository.findByAlias(username);

        if (!u.isPresent()) {
            UserEntity myUser = new UserEntity(username, passwordEncoder.encode(password), email, "USER");
            userRepository.save(myUser);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Logs you in after you register automatically
     * @param request
     * @param username
     * @param psw
     */
    public void authAfterRegister(HttpServletRequest request, String username, String psw) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, psw);
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * requests to the database all the users
     *
     * @return arraylist with all the users
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * asks the database for a espacific user
     *
     * @param name username of the user we need
     * @return the user or null if it doesn't exist
     */
    public UserEntity getUser(String name) {
        Optional<UserEntity> u = userRepository.findByAlias(name);
        if (u.isPresent())
            return u.get();
        else
            return null;
    }

    /**
     * asks the database for a espacific user
     *
     * @param id of the user we need
     * @return the user or null if it doesn't exist
     */
    public UserEntity getUser(long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        else
            return null;
    }

    /**
     * asks the database to update the information of a user
     *
     * @param user user updated
     */
    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }

    /**
     * Asks for all the teams a member is in because a user can be in diferente teams if they are from different jams
     * @param id of the user
     * @return a list with all the teams the user is in
     */
    public List<TeamEntity> getUserTeams(long id) {
        return teamRepository.findByMembersId(id);
    }

    /**
     * gets all the games from all the teams a user is in
     * @param teams teams the user is in
     * @return a list of videogames
     */
    public List<VideogameEntity> getGames(List<TeamEntity> teams) {
        List<VideogameEntity> games = new LinkedList<VideogameEntity>();

        for (TeamEntity t : teams) {
            games.add(t.getVideogame());
        }
        return games;
    }


}
