package es.urjc.dad.devNest.Internal_Services;

import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Database.Repositories.TeamRepository;
import es.urjc.dad.devNest.Database.Repositories.VideogameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Class responsible of the management of games. It provides service to add a new game to the data base, request an existing game
 * updating a game or downloading a game
 */
@Component
@SessionScope
public class GameService {
    @Autowired
    VideogameRepository videogameRepository;
    @Autowired
    TeamRepository teamRepository;

    /**
     * Add a new game to the database
     *
     * @param videogame
     * @param tName     team name
     * @return true if success
     */
    public boolean addNewGame(VideogameEntity videogame, String tName) {
        Optional<VideogameEntity> u = videogameRepository.findById(videogame.getId());
        if (!u.isPresent()) {

            //hay un error por aqui
            videogameRepository.save(videogame);
            TeamEntity t = teamRepository.findByTeamName(tName).get();
            t.setVideogame(videogame);
            teamRepository.save(t);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get an existing game from the data base
     *
     * @param id of the game
     * @return the game inside an optional
     */
    public VideogameEntity getGame(long id) {
        Optional<VideogameEntity> u = videogameRepository.findById(id);

        if (u.isPresent())
            return u.get();
        else
            return null;
    }

    /**
     * updates a game in the database
     *
     * @param v
     */
    public void updateGame(VideogameEntity v) {
        videogameRepository.save(v);
    }

    /**
     * Requests the REST to download the game from the browser
     *
     * @param id of the gam we want to download
     * @return invocation to the method in the rest to download the zip
     */
    public ResponseEntity<ByteArrayResource> downloadGame(long id) {
        RestTemplate restTemplate = new RestTemplate();
        URI url = null;
        try {
            url = new URI("http://localhost:8080/download-videogame/" + id + "/");
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return restTemplate.getForEntity(url, ByteArrayResource.class);
    }

}
