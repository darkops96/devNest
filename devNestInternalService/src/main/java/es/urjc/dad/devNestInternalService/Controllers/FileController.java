package es.urjc.dad.devNestInternalService.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.dad.devNestInternalService.Internal_Services.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class FileController {
    
    @Autowired
    private FileService fileService;

    @GetMapping("/download-videogame/{id}")
    public ResponseEntity<String> getVideogame(@PathVariable long id){
        if(fileService.download(id)){
            
        }
        else{

        }
        
        
        return ResponseEntity.ok("Found File");
    }
}
