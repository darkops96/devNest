package es.urjc.dad.devNestInternalService.Controllers;

import es.urjc.dad.devNestInternalService.Internal_Services.EmailService;
import es.urjc.dad.devNestInternalService.Objets.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/emails")
public class EmailController{

    @Autowired
    EmailService emailService;
    @Autowired


    @GetMapping("/registration-email/{id}")
    public ResponseEntity<Email> sendRegistrationEmail(@PathVariable long id) {

        String userEmail=

        Email email = new Email(/*acceder al correo del usuario*/"charlesdkch@gmail.com", "Registro devNest ", "EnHorabuena, te has tegistrado como usuario de devNest");
        emailService.sendEmail(email);
        return ResponseEntity.ok(email);
    }


}
