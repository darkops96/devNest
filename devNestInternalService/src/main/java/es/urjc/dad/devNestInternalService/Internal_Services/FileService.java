package es.urjc.dad.devNestInternalService.Internal_Services;

import java.sql.Blob;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.urjc.dad.devNestInternalService.Database.Entities.VideogameEntity;
import es.urjc.dad.devNestInternalService.Database.Repositories.VideogameRepository;


@Service
public class FileService {

    @Autowired
    private VideogameRepository videogameRepository;

    
    public ResponseEntity<ByteArrayResource> download(long id) throws Exception{
        /* DESCARGAR UN ZIP*/
        
        Optional<VideogameEntity> videogame = videogameRepository.findById(id);
        VideogameEntity vj = videogame.get();//Meter en el if
        Blob file = vj.getGameFile();//Meter en el if
        try{
            return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/zip"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+vj.getTitle()+".zip\"")
            .body(new ByteArrayResource(file.getBytes(1, (int) file.length())));    
        }
        catch(Exception e)
        {
            throw new Exception("Error downloading file");
        }   
    }
    
    public boolean isDownloable(long id){
        Optional<VideogameEntity> videogame = videogameRepository.findById(id);
        return videogame.isPresent();
    }
}
