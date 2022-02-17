package es.urjc.dad.devNest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DevNestController
{
    
    @GetMapping("/")
    public String home(Model model){
        
        //Random generator
        model.addAttribute("topic1", "Oscuridad");
        model.addAttribute("topic2", "Saltos");

        //GameJams table
        model.addAttribute("name", "GameGen GameJam");
        model.addAttribute("topics", "Oscuridad/Salto");
        model.addAttribute("teams", "Team 3\nTeam 16");
        model.addAttribute("startdate", "02/23/2021");
        model.addAttribute("deadline", "02/26/2021");
        model.addAttribute("winner", "Team 3");

        return "initialWeb";
    }

    @GetMapping("/login")
    public String goToLogin(Model model)
    {
        return "loginWeb";
    }

    @GetMapping("/register")
    public String goToRegister(Model model)
    {
        return "registerWeb";
    }
}
