package es.urjc.dad.devNestInternalService.Controllers;

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

    /**
     * starts  the download when the aplication in GameController asks for it
     *
     * @param id of the game that has to be downloaded
     * @return game file
     * @throws Exception
     */
    @GetMapping("/download-videogame/{id}")
    public ResponseEntity<ByteArrayResource> getVideogame(@PathVariable long id) throws Exception {
        if (fileService.isDownloable(id))
            return fileService.download(id);
        else
            return (ResponseEntity<ByteArrayResource>) ResponseEntity.notFound();
    }
}
