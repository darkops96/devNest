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

@Service
@EnableAsync
public class AsyncEmailService
{    
    @Async
    public void sendRegisterEmail(String username, String email) throws RestClientException, URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8080/emails/registration-email/");

        List<String> data = new ArrayList<>(2);
        data.add(username);
        data.add(email);        

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List> requestEntity = new HttpEntity<>(data, headers);

        restTemplate.postForEntity(url, requestEntity, String.class);
    }

    @Async
    public void sendRegisterJam(String username, String email, String jam) throws RestClientException, URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8080/emails/create-jam/");

        List<String> data = new ArrayList<>(3);
        data.add(username);
        data.add(email); 
        data.add(jam);   

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List> requestEntity = new HttpEntity<>(data, headers);

        restTemplate.postForEntity(url, requestEntity, String.class);
    }

    @Async
    public void sendJoinTeam(String username, String email, String team) throws RestClientException, URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8080/emails/join-team/");

        List<String> data = new ArrayList<>(3);
        data.add(username);
        data.add(email); 
        data.add(team);   

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List> requestEntity = new HttpEntity<>(data, headers);

        restTemplate.postForEntity(url, requestEntity, String.class);
    }
}
