package es.urjc.dad.devNestInternalService.Internal_Services;

import java.sql.Blob;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import es.urjc.dad.devNestInternalService.Database.Entities.VideogameEntity;
import es.urjc.dad.devNestInternalService.Database.Repositories.VideogameRepository;

/**
 * Class in charge of implementing the service of downloading the file in the browser
 */
@Service
@EnableAsync
public class FileService {

    @Autowired
    private VideogameRepository videogameRepository;

    /**
     * Downloads a ZIP file from the browser
     *
     * @param id of the game
     * @return the game in the body of the response entity or not found if it doesnt exist
     * @throws Exception
     */
    @Async
    public CompletableFuture<ResponseEntity<ByteArrayResource>> download(long id) throws Exception {
        /* DESCARGAR UN ZIP*/
        //get the game from the database
        VideogameEntity vj = videogameRepository.findById(id).get();
        Blob file = vj.getGameFile();
        //if the game is present then it tries to download it
        try {
            return CompletableFuture.completedFuture(ResponseEntity.ok().contentType(MediaType.parseMediaType("application/zip"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + vj.getTitle() + ".zip\"")
            .body(new ByteArrayResource(file.getBytes(1, (int) file.length()))));
        } catch (Exception e) {
            throw new Exception("Error downloading file");
        }
    }

    /**
     * Auxiliar method to check if a game is in the database
     *
     * @param id of the game
     * @return true if is present and false if not
     */
    public boolean isDownloable(long id) {
        Optional<VideogameEntity> videogame = videogameRepository.findById(id);
        return videogame.isPresent();
    }
}
