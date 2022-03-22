package es.urjc.dad.devNestInternalService.Controllers;

import es.urjc.dad.devNestInternalService.Database.Entities.GamejamEntity;
import es.urjc.dad.devNestInternalService.Database.Entities.UserEntity;
import es.urjc.dad.devNestInternalService.Database.Entities.VideogameEntity;
import es.urjc.dad.devNestInternalService.Database.Repositories.GamejamRepository;
import es.urjc.dad.devNestInternalService.Database.Repositories.UserRepository;
import es.urjc.dad.devNestInternalService.Database.Repositories.VideogameRepository;
import es.urjc.dad.devNestInternalService.Internal_Services.EmailService;
import es.urjc.dad.devNestInternalService.Objets.Email;

import java.util.List;

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
    @Autowired
    private VideogameRepository videogameRepository;

    //email when you register    
    @PostMapping("/registration-email")
    public void sendRegistrationEmail(@RequestBody List<String> data)
    {
        Email email = new Email(data.get(1), "Registro devNest ", "¡Enhorabuena "+ data.get(0) + "!\nTe has registrado como usuario de devNest.\n\nDisfruta de tu experiencia con nosotros :D");
        emailService.sendEmail(email);
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
    @GetMapping("/submit-game/{id}{idUser}")
    public ResponseEntity<Email> sendSubmitGameEmail(@PathVariable long id, @PathVariable long idUser) {

        UserEntity user = userRepository.findById(idUser).get();
        VideogameEntity videogame = videogameRepository.findById(id).get();

        String userEmail = user.getEmail();

        Email email = new Email(userEmail, "Subiste el juego " + videogame.getTitle(), "Enhorabuena, tu juego " + videogame.getTitle() + " se ha subido con exito");
        emailService.sendEmail(email);
        return ResponseEntity.ok(email);
    }


}
