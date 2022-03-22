import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
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


    public ResponseEntity download(long id) throws Exception{
        /* DESCARGAR UN ZIP*/
        /*
        Optional<VideogameEntity> videogame = videogameRepository.findById(id);
        VideogameEntity vj = videogame.get();//Meter en el if
        Blob file = vj.getGameFile();//Meter en el if
        try{
            return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/zip"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+vj.getId()+"\"")
            .body(new ByteArrayResource(file.getBytes(1, (int) file.length())));    
        }
        catch(Exception e)
        {
            throw new Exception("Error downloading file");
        }   */
        /* PARA COMPROBAR CON UN ARCHIVO DEL ORDENADOR: */
        String filename = "D:/Archivos de Programa/Escritorio/devNest/devNestInternalService/Zip-to-delete/hola.zip";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires","0");
        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/zip")).body(resource);
        return responseEntity;
    }
    
    public boolean isDownloable(long id){
        Optional<VideogameEntity> videogame = videogameRepository.findById(id);
        return videogame.isPresent();
    }
}
