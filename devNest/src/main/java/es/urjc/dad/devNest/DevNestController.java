package es.urjc.dad.devNest;

import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Internal_Services.GameJamService;
import es.urjc.dad.devNest.Internal_Services.RandomWord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.urjc.dad.devNest.Internal_Services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DevNestController
{

    @Autowired
    private UserService userService;
    @Autowired
    private GameJamService gameJamService;

    @Autowired
    private RandomWord randomWord;    
    
    @GetMapping("/")
    public String home(Model model){
        
        //Random generator
        model.addAttribute("topic1", randomWord.getRandomWord());
        model.addAttribute("topic2", randomWord.getRandomWord());

        model.addAttribute("gamejams", gameJamService.getAllJams());        

        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);

        return "initialWeb";
    }


    @GetMapping("/login")
    public String goToLogin(Model model) {
        return "loginWeb";
    }

    @GetMapping("/register")
    public String goToRegister(Model model) {
        return "registerWeb";
    }

    @RequestMapping(value="/loginUser")
    public String login(@RequestParam String username, @RequestParam String psw)
    {
        boolean result = userService.login(username, psw);
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
    public ModelAndView logout(Model model)
    {
        userService.logout();
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/myProfile")
    public String goToMyProfile(Model model) 
    {
        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);
        return "profileWeb";
    }

    @RequestMapping("/gamejam/{gjId}")
    public String jamPage(Model model, @PathVariable long gjId) {
        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);

        model.addAttribute("gamejam", gameJamService.getJam(gjId));
        return "gameJamWeb";
    }

    @GetMapping("/registerJam")
    public String goToOrganizeJam(Model model)
    {
        return "createJam";
    }
}
