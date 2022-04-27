package es.urjc.dad.devNestInternalService.Controllers;

import java.util.concurrent.CompletableFuture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.urjc.dad.devNestInternalService.Internal_Services.FileService;

/**
 * This class is in charge of asking the service to download a file in the browser when the aplication asks for it
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    private static final Log logger = LogFactory.getLog(FileController.class);

    /**
     * starts  the download when the aplication in GameController asks for it
     *
     * @param id of the game that has to be downloaded
     * @return game file
     * @throws Exception
     */
    @GetMapping("/videogame-file/{id}")
    public ResponseEntity<ByteArrayResource> getVideogame(@PathVariable long id) throws Exception {
        logger.info("GET /videogame-file/" + id);
        
        if (fileService.isDownloable(id))
        {
            CompletableFuture<ResponseEntity<ByteArrayResource>> result = fileService.download(id);
            if(result.isCancelled())
                return (ResponseEntity<ByteArrayResource>) ResponseEntity.notFound();
            else  
                return result.get();
        }
        else
            return (ResponseEntity<ByteArrayResource>) ResponseEntity.notFound();
    }
}
