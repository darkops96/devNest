package es.urjc.dad.devNest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class DevNestLoginController {

    @GetMapping("/Login")
    public String login(Model model){
        //Random generator
        model.addAttribute("name", "aaaaaaaaa");


        return "loginWeb";
    }
}
