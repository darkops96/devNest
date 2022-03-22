package es.urjc.dad.devNestInternalService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevNestInternalServiceApplication {

	public static void main(String[] args)
	{
		String newLine = System.lineSeparator();
        System.out.println(newLine +
                           "********************************" + newLine +
                           "******* DEVNEST SERVICES *******" + newLine +
                           "********************************" + newLine +
                           "Developed by:" + newLine +
                           "- Pablo de la Hoz Menendez" + newLine +
                           "- Carlos Chamizo Cano" + newLine +
                           "- Juan Jose Perez Abad" + newLine +
                           "********************************" + newLine);
		
		SpringApplication.run(DevNestInternalServiceApplication.class, args);
	}

}
