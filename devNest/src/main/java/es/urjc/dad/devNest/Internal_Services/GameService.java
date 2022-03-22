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

@Component
@SessionScope
public class GameService {
    @Autowired
    VideogameRepository videogameRepository;
    @Autowired
    TeamRepository teamRepository;

    //add a new game
    public boolean addNewGame(VideogameEntity videogame, String tName) {
        Optional<VideogameEntity> u = videogameRepository.findById(videogame.getId());
        if (!u.isPresent()) {
            videogameRepository.save(videogame);
            TeamEntity t = teamRepository.findByTeamName(tName).get();
            t.setVideogame(videogame);
            teamRepository.save(t);            
            return true;
        } else {
            return false;
        }
    }

    //get a game
    public VideogameEntity getGame(long id) {
        Optional<VideogameEntity> u = videogameRepository.findById(id);

        if (u.isPresent())
            return u.get();
        else
            return null;
    }

    public void updateGame(VideogameEntity v)
    {
        videogameRepository.save(v);
    }

    public ResponseEntity<ByteArrayResource> downloadGame(long id){
        RestTemplate restTemplate = new RestTemplate();
        URI url = null;
        try {
            url = new URI("http://localhost:8080/download-videogame/"+id+"/");
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return restTemplate.getForEntity(url, ByteArrayResource.class);
        

    }

}
