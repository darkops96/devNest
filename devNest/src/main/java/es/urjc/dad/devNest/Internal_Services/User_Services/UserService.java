package es.urjc.dad.devNest.Internal_Services.User_Services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
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
    @PostConstruct
    private void initDatabase()
    {
        Optional<UserEntity> u = userRepository.findByAlias("admin");

        if (!u.isPresent()) {
            userRepository.save(new UserEntity("admin", adminPassword, "admin@devnest.es", "USER", "ADMIN"));
        }
    }
    //endregion

    public boolean register(String username, String password, String email)
    {
        Optional<UserEntity> u = userRepository.findByAlias(username);

        if (!u.isPresent()) {
            UserEntity myUser = new UserEntity(username, passwordEncoder.encode(password), email, "USER");
            userRepository.save(myUser);
            return true;
        } else {
            return false;
        }
    }    

    public void authAfterRegister(HttpServletRequest request, String username, String psw)
    {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, psw);
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void sendRegisterEmail(String username, String email) throws RestClientException, URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8080/emails/registration-email/");

        List<String> data = new ArrayList<>(2);
        data.add(username);
        data.add(email);        

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List> requestEntity = new HttpEntity<>(data, headers);

        restTemplate.postForEntity(url, requestEntity, String.class);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUser(String name) {
        Optional<UserEntity> u = userRepository.findByAlias(name);        
        if(u.isPresent())
            return u.get();
        else
            return null;
    }

    public UserEntity getUser(long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        else
            return null;
    }

    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }

    public List<TeamEntity> getUserTeams(long id) {
        return teamRepository.findByMembersId(id);
    }

    public List<VideogameEntity> getGames(List<TeamEntity> teams) {
        List<VideogameEntity> games = new LinkedList<VideogameEntity>();

        for (TeamEntity t : teams) {
            games.add(t.getVideogame());
        }
        return games;
    }


}
