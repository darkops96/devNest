package es.urjc.dad.devNest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.urjc.dad.devNest.Internal_Services.RandomWord;

@SpringBootApplication
public class DevNestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevNestApplication.class, args);
    }

}
