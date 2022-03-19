package es.urjc.dad.devNestInternalService.Internal_Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.dad.devNestInternalService.Database.Entities.VideogameEntity;
import es.urjc.dad.devNestInternalService.Database.Repositories.VideogameRepository;


@Service
public class FileService {

    @Autowired
    private VideogameRepository videogameRepository;


    public boolean download(long id){
        Optional<VideogameEntity> videogame = videogameRepository.findById(id);
        if(videogame.isPresent()){
            return true;
        }
        else{
            return false;
        }

    }
}
