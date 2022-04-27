package es.urjc.dad.devNestInternalService.Controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import es.urjc.dad.devNestInternalService.Internal_Services.RandomWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Class in charge of implementing the Random Word service. Its funcition is to ask to he wor service for 2 words and send them to the aplication.
 */
@RestController
public class RandomWordController {
    @Autowired
    private RandomWordService randomWordService;

    private static final Log logger = LogFactory.getLog(RandomWordController.class);

    /**
     * asks for 2 words to the service and sends them to the aplication in the body of the response entity
     * it  is used in the aplication in -> random word service
     *
     * @return the 2 words inside the response entity body
     */
    @GetMapping("/random-topics")
    public ResponseEntity<ArrayList> getRandomTopics() {
        logger.info("GET /random-topics");
        
        ArrayList<String> topics = new ArrayList<String>(2);
        try
        {            
            topics.add(randomWordService.getRandomWord().get());
            topics.add(randomWordService.getRandomWord().get());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(topics);
        } 
        catch (InterruptedException | ExecutionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return (ResponseEntity<ArrayList>) ResponseEntity.notFound();
        }
    }
}
