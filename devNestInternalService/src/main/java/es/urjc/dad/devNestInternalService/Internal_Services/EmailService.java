package es.urjc.dad.devNestInternalService.Internal_Services;

import es.urjc.dad.devNestInternalService.Objets.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * Service in charge of actually sending the email to the destinatary
 */
@Service
@EnableAsync
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Actually sends the email
     * @param e email which conteins the subject, destinatary, and body
     */
    @Async
    public void sendEmail(Email e) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(e.getDestination());
        email.setSubject(e.getSubject());
        email.setText(e.getContent());

        mailSender.send(email);
    }
}
