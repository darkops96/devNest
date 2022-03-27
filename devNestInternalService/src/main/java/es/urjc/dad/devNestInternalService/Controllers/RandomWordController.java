package es.urjc.dad.devNestInternalService.Controllers;

import es.urjc.dad.devNestInternalService.Internal_Services.RandomWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Class in charge of implementing the Random Word service. Its funcition is to ask to he wor service for 2 words and send them to the aplication.
 */
@RestController
public class RandomWordController {
    @Autowired
    private RandomWordService randomWordService;

    /**
     * asks for 2 words to the service and sends them to the aplication in the body of the response entity
     * it  is used in the aplication in -> random word service
     *
     * @return the 2 words inside the response entity body
     */
    @GetMapping("/random-topics")
    public ResponseEntity<ArrayList> getRandomTopics() {
        ArrayList<String> topics = new ArrayList<String>(2);
        topics.add(randomWordService.getRandomWord());
        topics.add(randomWordService.getRandomWord());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(topics);
    }
}
