package es.urjc.dad.devNest.Internal_Services;

import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Database.Repositories.TeamRepository;
import es.urjc.dad.devNest.Database.Repositories.VideogameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

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


}
