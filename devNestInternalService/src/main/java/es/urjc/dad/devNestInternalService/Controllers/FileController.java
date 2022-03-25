package es.urjc.dad.devNestInternalService.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.urjc.dad.devNestInternalService.Internal_Services.FileService;

@RestController
public class FileController {
    
    @Autowired
    private FileService fileService;

    @GetMapping("/download-videogame/{id}")
    public ResponseEntity<ByteArrayResource> getVideogame(@PathVariable long id) throws Exception
    {
        if(fileService.isDownloable(id)){
            return fileService.download(id);
        }
        else{       
            return  (ResponseEntity<ByteArrayResource>) ResponseEntity.notFound();
        }
    }  
}
