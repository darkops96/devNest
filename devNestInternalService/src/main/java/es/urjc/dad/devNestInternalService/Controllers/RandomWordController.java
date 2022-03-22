package es.urjc.dad.devNestInternalService.Controllers;

import es.urjc.dad.devNestInternalService.Internal_Services.RandomWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;


@RestController
public class RandomWordController {
    @Autowired
    private RandomWordService randomWordService;

    @GetMapping("/random-topics")
    public ResponseEntity<ArrayList> getRandomTopics() {
        ArrayList<String> topics = new ArrayList<String>(2);
        topics.add(randomWordService.getRandomWord());
        topics.add(randomWordService.getRandomWord());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(topics);
    }
}
