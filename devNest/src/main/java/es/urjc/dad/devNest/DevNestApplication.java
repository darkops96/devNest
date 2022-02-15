package es.urjc.dad.devNest;

import es.urjc.dad.devNest.Utils.RandomWord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevNestApplication {

    public static void main(String[] args) {
        new RandomWord();
        SpringApplication.run(DevNestApplication.class, args);
    }

}
