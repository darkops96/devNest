package es.urjc.dad.devNest;

import es.urjc.dad.devNest.Internal_Services.RandomWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DevNestController {

    @Autowired
    RandomWord randomWord;

    //region INITIAL WEB
    @GetMapping("/")
    public String home(Model model) {

        //Random generator
        model.addAttribute("topic1", randomWord.getRandomWord());
        model.addAttribute("topic2", randomWord.getRandomWord());

        //GameJams table
        model.addAttribute("name", "GameGen GameJam");
        model.addAttribute("topics", "Oscuridad/Salto");
        model.addAttribute("teams", "Team 3\nTeam 16");
        model.addAttribute("startdate", "02/23/2021");
        model.addAttribute("deadline", "02/26/2021");
        model.addAttribute("winner", "Team 3");

        return "initialWeb";
    }
    //endregion


    @GetMapping("/login")
    public String goToLogin(Model model) {
        return "loginWeb";
    }

    @GetMapping("/register")
    public String goToRegister(Model model) {
        return "registerWeb";
    }
}
