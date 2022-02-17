package es.urjc.dad.devNest.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DevNestController {
    
    @GetMapping("/")
    public String home(Model model){
        //Random generator
        model.addAttribute("topic1", "Oscuridad");
        model.addAttribute("topic2", "Saltos");

        //GameJams table
        model.addAttribute("gamejam1", "GameGen GameJam");
        model.addAttribute("topics1", "Oscuridad/Salto");
        model.addAttribute("teams1", "Team 3\nTeam 16");
        model.addAttribute("start1", "02/23/2021");
        model.addAttribute("end1", "02/26/2021");
        model.addAttribute("winner1", "Team 3");

        return "initialWeb";
    }
}
