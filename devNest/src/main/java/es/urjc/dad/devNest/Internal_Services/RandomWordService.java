package es.urjc.dad.devNest.Internal_Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is responsible of requesting to the REST the service corresponding to random words
 */
@Service
public class RandomWordService {
    
    @Value("${internalService.baseUri}")
    private String serviceBaseUri;
    
    /**
     * Asks the REST for 2 new topics and will be used in the
     * used in home and register a jam
     *
     * @return obtains the 2 words inside an arraylist
     */
    public List<String> getRandomWord() {
        RestTemplate restTemplate = new RestTemplate();
        URI url = null;
        try {
            //url of the controller in the REST
            url = new URI(serviceBaseUri + "/random-topics/");
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //gets the 2 words in an arrayList from the rest which goes inside the responsebody
        ResponseEntity<ArrayList> responseEntity = restTemplate.getForEntity(url, ArrayList.class);
        ArrayList<String> topics = responseEntity.getBody();
        return topics;
    }
}
