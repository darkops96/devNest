package es.urjc.dad.devNest;

import javax.servlet.http.HttpSession;
import es.urjc.dad.devNest.Internal_Services.RandomWord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.urjc.dad.devNest.Internal_Services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class DevNestController
{

    @Autowired
    private UserService userService;
    @Autowired
    private RandomWord randomWord;
    
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

    @RequestMapping(value="/loginUser", method = RequestMethod.POST, params={"username", "password"})
    public String login(@RequestParam String username, @RequestParam String password)
    {
        boolean result = userService.login(username, password);
        if(result)
        {
            return "redirect:/";
        }
        else
        {
            return "redirect:/login";
        } 
    }

    @RequestMapping(value="/registerUser")
    public ModelAndView register(@RequestParam String username, @RequestParam String psw, @RequestParam String email, @RequestParam String pswRepeat)
    {
        if(psw.equals(pswRepeat))
        {
            boolean result = userService.register(username, psw, email);
            if(result)
            {
                return new ModelAndView("redirect:/");
            }
            else
            {
                return new ModelAndView("redirect:/register");
            }
        }
        else
        {
            return new ModelAndView("redirect:/register");
        }                
    }

    @GetMapping("/logout")
    public ModelAndView logout(Model model, HttpSession httpSession)
    {
        userService.logout(httpSession);
        return new ModelAndView("redirect:/");
    }
}
