package es.urjc.dad.devNestInternalService.Controllers;

import es.urjc.dad.devNestInternalService.Internal_Services.EmailService;
import es.urjc.dad.devNestInternalService.Objets.Email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    //email when you register (Username, email)
    @PostMapping("/registration-email")
    public void sendRegistrationEmail(@RequestBody List<String> data)
    {
        Email email = new Email(data.get(1), "Registro devNest ", "¡Enhorabuena "+ data.get(0) + "!\nTe has registrado como usuario de devNest.\n\nDisfruta de tu experiencia con nosotros :D");
        emailService.sendEmail(email);
    }

    //email when you create a jam (Username, email, Jam name)
    @PostMapping("/create-jam")
    public void sendCreationOfJamEmail(@RequestBody List<String> data)
    {
        Email email = new Email(data.get(1), "Tu Jam ha sido registrada", "Enhorabuena" + data.get(0) + ", tu Jam " + data.get(2) + " ha sido registrada con exito");
        emailService.sendEmail(email);
    }

    //email when you join a team (Username, email, Team name)
    @PostMapping("/join-team")
    public void sendJoinTeamEmail(@RequestBody List<String> data)
    {
        Email email = new Email(data.get(1), data.get(0) + " te has unido a un equipo", "Enhorabuena "+data.get(0)+",\nTe has unido al equipo " + data.get(2)+".");
        emailService.sendEmail(email);
    }

    //email when you submit a videogame (Game name, number of members, emails)
    @PostMapping("/submit-game")
    public void sendSubmitGameEmail(@RequestBody List<String> data)
    {
        int numEmails = Integer.parseInt(data.get(1));

        for(int i = 2; i < 2 + numEmails; i++)
        {
            Email email = new Email(data.get(i), "Tu juego " + data.get(0) + " se ha subido con exito", "Enhorabuena, tu juego " + data.get(0) + " se ha subido con exito.");
            emailService.sendEmail(email);
        }        
    }


}
