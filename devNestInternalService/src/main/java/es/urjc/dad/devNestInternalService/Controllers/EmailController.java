package es.urjc.dad.devNestInternalService.Controllers;

import es.urjc.dad.devNestInternalService.Database.Entities.GamejamEntity;
import es.urjc.dad.devNestInternalService.Database.Entities.UserEntity;
import es.urjc.dad.devNestInternalService.Database.Repositories.GamejamRepository;
import es.urjc.dad.devNestInternalService.Database.Repositories.UserRepository;
import es.urjc.dad.devNestInternalService.Internal_Services.EmailService;
import es.urjc.dad.devNestInternalService.Objets.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GamejamRepository gamejamRepository;

    //email when you register
    @GetMapping("/registration-email/{id}")
    public ResponseEntity<Email> sendRegistrationEmail(@PathVariable long id) {

        UserEntity user = userRepository.findById(id).get();
        String userEmail = user.getEmail();

        Email email = new Email(userEmail, "Registro devNest ", "Enhorabuena, te has tegistrado como usuario de devNest con usuario " + user.getAlias() + " y contraseña " + user.getPassword());
        emailService.sendEmail(email);
        return ResponseEntity.ok(email);
    }

    //email when you create a jam
    @GetMapping("/create-Jam/{id}")
    public ResponseEntity<Email> sendCreationOfJamEmail(@PathVariable long id) {

        GamejamEntity gamejam = gamejamRepository.findById(id).get();
        UserEntity user = gamejam.getAdminUser();
        String userEmail = user.getEmail();

        Email email = new Email(userEmail, "la Jam " + gamejam.getName() + " ha sido registrada", "Enhorabuena" + user.getAlias() + ", tu Jam " + gamejam.getName() + " ha sido registrada con exito");
        emailService.sendEmail(email);
        return ResponseEntity.ok(email);
    }

    //email when you join a team
    @GetMapping("/join-team/{id}")
    public ResponseEntity<Email> sendJoinTeamEmail(@PathVariable long id, @RequestParam String teamName) {

        UserEntity user = userRepository.findById(id).get();
        String userEmail = user.getEmail();

        Email email = new Email(userEmail, user.getAlias() + " te has unido a un equipo ", "Enhorabuena, te has unido al equipo" + teamName);
        emailService.sendEmail(email);
        return ResponseEntity.ok(email);
    }

    //email when you submit a videogame
    @GetMapping("/submit-game/{id}")
    public ResponseEntity<Email> sendSubmitGameEmail(@PathVariable long id) {

        UserEntity user = userRepository.findById(id).get();
        String userEmail = user.getEmail();

        Email email = new Email(userEmail, "Registro devNest ", "Enhorabuena, te has tegistrado como usuario de devNest con usuario " + user.getAlias() + " y contraseña " + user.getPassword());
        emailService.sendEmail(email);
        return ResponseEntity.ok(email);
    }


}
