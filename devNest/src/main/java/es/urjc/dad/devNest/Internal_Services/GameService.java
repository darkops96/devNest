package es.urjc.dad.devNest.Internal_Services;

import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
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

    //add a new game
    public boolean addNewGame(VideogameEntity videogame) {
        VideogameEntity newJam = videogame;
        Optional<VideogameEntity> u = videogameRepository.findById(newJam.getId());
        if (!u.isPresent()) {
            videogameRepository.save(newJam);
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
