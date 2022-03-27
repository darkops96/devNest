package es.urjc.dad.devNestInternalService.Controllers;

import es.urjc.dad.devNestInternalService.Internal_Services.EmailService;
import es.urjc.dad.devNestInternalService.Objets.Email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Class in charge of implementing the email service needed to the aplication. Its funcition is to recive the user email and
 * some extra information so the Email service can correctly send it.
 */
@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     * creates the email when aplication Registers a user. This method its called in the aplication -> AsyncEmailService
     *
     * @param data contains the username (0) and email (1)
     */
    @PostMapping("/registration-email")
    public void sendRegistrationEmail(@RequestBody List<String> data) {
        Email email = new Email(data.get(1), "Registro devNest ", "¡Enhorabuena " + data.get(0) + "!\nTe has registrado como usuario de devNest.\n\nDisfruta de tu experiencia con nosotros :D");
        emailService.sendEmail(email);
    }

    /**
     * creates the email when aplication Registers a Jam. This method its called in the aplication -> AsyncEmailService
     *
     * @param data contains the username (0), email (1) and jam (2)
     */
    @PostMapping("/create-jam")
    public void sendCreationOfJamEmail(@RequestBody List<String> data) {
        Email email = new Email(data.get(1), "Tu Jam ha sido registrada", "Enhorabuena " + data.get(0) + ", tu Jam " + data.get(2) + " ha sido registrada con exito");
        emailService.sendEmail(email);
    }

    /**
     * creates the email when aplication jois a user into a team. This method its called in the aplication -> AsyncEmailService
     *
     * @param data contains the username (0), email (1) and team name (2)
     */
    @PostMapping("/join-team")
    public void sendJoinTeamEmail(@RequestBody List<String> data) {
        Email email = new Email(data.get(1), data.get(0) + " te has unido a un equipo", "Enhorabuena " + data.get(0) + ",\nTe has unido al equipo " + data.get(2) + ".");
        emailService.sendEmail(email);
    }

}
