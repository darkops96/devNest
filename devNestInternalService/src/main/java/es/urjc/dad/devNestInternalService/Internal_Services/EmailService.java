package es.urjc.dad.devNestInternalService.Internal_Services;

import es.urjc.dad.devNestInternalService.Database.Repositories.UserRepository;
import es.urjc.dad.devNestInternalService.Objets.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;



    //Pasamos por parametro: destinatario, asunto y el mensaje
    public void sendEmail(Email e) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(e.getDestination());
        email.setSubject(e.getSubject());
        email.setText(e.getContent());

        mailSender.send(email);
    }
}
