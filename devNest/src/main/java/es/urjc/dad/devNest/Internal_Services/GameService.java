package es.urjc.dad.devNest.Internal_Services;

import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Database.Repositories.TeamRepository;
import es.urjc.dad.devNest.Database.Repositories.VideogameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Class responsible of the management of games. It provides service to add a new game to the data base, request an existing game
 * updating a game or downloading a game
 */
@Service
public class GameService {
    @Autowired
    VideogameRepository videogameRepository;
    @Autowired
    TeamRepository teamRepository;

    @Value("${internalService.baseUri}")
    private String serviceBaseUri;

    /**
     * Add a new game to the database
     *
     * @param videogame
     * @param tId       team id
     * @return true if success
     */
    public boolean addNewGame(VideogameEntity videogame, long tId) {
        Optional<VideogameEntity> u = videogameRepository.findById(videogame.getId());
        if (!u.isPresent()) {
            setTeamGame(tId, videogame);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets or updates the game of a team in the database
     *
     * @param tId
     * @param v
     */
    private void setTeamGame(long tId, VideogameEntity v) {
        Optional<TeamEntity> t = teamRepository.findById(tId);
        if (t.isPresent()) {
            t.get().setVideogame(v);
            teamRepository.save(t.get());
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
     * Updates a game in the database
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
            url = new URI(serviceBaseUri + "/videogame-file/" + id + "/");
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return restTemplate.getForEntity(url, ByteArrayResource.class);


    }

}
