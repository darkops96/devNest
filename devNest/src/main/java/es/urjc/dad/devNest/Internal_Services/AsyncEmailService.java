package es.urjc.dad.devNest.Internal_Services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Class responsible for asking the rest to send the emails for especidic actions
 */
@Service
@EnableAsync
public class AsyncEmailService {
    /**
     * Asks the Rest to send an email when the user correctly registers
     *
     * @param username user username
     * @param email    user email
     * @throws RestClientException
     * @throws URISyntaxException
     */
    @Async
    public void sendRegisterEmail(String username, String email) throws RestClientException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        //URL of the controller in charge of sending the emails
        URI url = new URI("http://localhost:8080/emails/registration/");
        //store the user and its email in an arraylist
        List<String> data = new ArrayList<>(2);
        data.add(username);
        data.add(email);

        //code needed to send the arraylist so the rest controller can recibe it on the body
        HttpEntity<List> requestEntity = getListHttpEntity(data);
        //sends the information in a post to the Rest
        restTemplate.postForEntity(url, requestEntity, String.class);
    }

    /**
     * Asks the Rest to send an email when the user correctly registers a jam
     *
     * @param username
     * @param email
     * @param jam
     * @throws RestClientException
     * @throws URISyntaxException
     */
    @Async
    public void sendRegisterJam(String username, String email, String jam) throws RestClientException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        //URL of the controller in charge of sending the emails
        URI url = new URI("http://localhost:8080/emails/jam-creation/");
        //store the user and its email in an arraylist
        List<String> data = new ArrayList<>(3);
        data.add(username);
        data.add(email);
        data.add(jam);

        HttpEntity<List> requestEntity = getListHttpEntity(data);

        restTemplate.postForEntity(url, requestEntity, String.class);
    }


    /**
     * Asks the Rest to send an email when the user correctly joins a team
     *
     * @param username user username
     * @param email    user email
     * @param team     team user is joining
     * @throws RestClientException
     * @throws URISyntaxException
     */
    @Async
    public void sendJoinTeam(String username, String email, String team) throws RestClientException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        //URL of the controller in charge of sending the emails
        URI url = new URI("http://localhost:8080/emails/new-team/");
        //store the user and its email in an arraylist
        List<String> data = new ArrayList<>(3);
        data.add(username);
        data.add(email);
        data.add(team);

        HttpEntity<List> requestEntity = getListHttpEntity(data);
        restTemplate.postForEntity(url, requestEntity, String.class);
    }

    /**
     * Auxiliar method for packaging the information we need to send to the REST
     *
     * @param data information that needs to be sent to the REST
     * @return an http entity with the information
     */
    private HttpEntity<List> getListHttpEntity(List<String> data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List> requestEntity = new HttpEntity<>(data, headers);
        return requestEntity;
    }
}
