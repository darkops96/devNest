package es.urjc.dad.devNest.Internal_Services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RandomWordService
{
    public List<String> getRandomWord() {
        RestTemplate restTemplate = new RestTemplate();
        URI url = null;
        try {
            url = new URI("http://localhost:8080/random-topics/");
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ResponseEntity<ArrayList> responseEntity = restTemplate.getForEntity(url, ArrayList.class);

        ArrayList<String> topics = responseEntity.getBody();

        return topics;
    }
}
