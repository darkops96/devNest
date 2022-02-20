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
public class DevNestController {

    @Autowired
    private UserService userService;
    @Autowired
    private GameJamService gameJamService;
    @Autowired
    private RandomWord randomWord;


    //region ./ controller
    @GetMapping("/")
    public String home(Model model) {

        //Random generator
        randomWordAction(model);

        model.addAttribute("gamejams", gameJamService.getAllJams());

        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);

        return "initialWeb";
    }

    //endregion

    //region login controller
    @GetMapping("/login")
    public String goToLogin() {
        return "loginWeb";
    }

    @RequestMapping(value = "/loginUser")
    public String login(@RequestParam String username, @RequestParam String psw) {
        boolean result = userService.login(username, psw);
        if (result) {
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    //endregion

    //region register user controller
    @GetMapping("/register")
    public String goToRegister() {
        return "registerWeb";
    }

    @RequestMapping(value = "/registerUser")
    public ModelAndView register(@RequestParam String username, @RequestParam String psw, @RequestParam String email, @RequestParam String pswRepeat) {
        if (psw.equals(pswRepeat)) {
            boolean result = userService.register(username, psw, email);
            if (result) {
                return new ModelAndView("redirect:/");
            } else {
                return new ModelAndView("redirect:/register");
            }
        } else {
            return new ModelAndView("redirect:/register");
        }
    }
    //endregion

    //region logout controller
    @GetMapping("/logout")
    public ModelAndView logout() {
        userService.logout();
        return new ModelAndView("redirect:/");
    }
    //endregion

    //region my profile controller
    @GetMapping("/myProfile")
    public String goToMyProfile(Model model) {
        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);
        model.addAttribute("videogame", userService.getGames(userService.getUserTeams(myUser.getId())));
        ;
        return "profileWeb";
    }

    @RequestMapping("/profile/{uId}")
    public String goToProfile(Model model, @PathVariable long uId) {
        UserEntity user = userService.getUser(uId);
        model.addAttribute("userEntity", user);
        model.addAttribute("videogame", userService.getGames(userService.getUserTeams(uId)));
        return "profile";
    }
    //endregion

    //region gamejam controller
    @RequestMapping("/gamejam/{gjId}")
    public String jamPage(Model model, @PathVariable long gjId) {
        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);

        model.addAttribute("gamejam", gameJamService.getJam(gjId));
        return "gameJamWeb";
    }

    @RequestMapping("/gamejam/{gjId}/register+team")
    public ModelAndView registerTeam(@PathVariable long gjId) {
        UserEntity myUser = userService.getMyUser();
        if (myUser != null) {
            gameJamService.addNewTeam(gjId, "Team 1", myUser);
        }
        return new ModelAndView("redirect:/gamejam/" + gjId);
    }
    //endregion

    //region register jam controller
    @GetMapping("/registerJam")
    public String goToOrganizeJam(Model model) {
        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);

        randomWordAction(model);
        return "createJam";
    }
    @RequestMapping(value = "/registerGameJam")
    public ModelAndView createAJam(@RequestParam String jamName, @RequestParam String description, @RequestParam String topic, @RequestParam String sDate, @RequestParam String eDate) {
        boolean result = gameJamService.addNewJam(jamName, description, userService.getMyUser(), topic, sDate, eDate);
        if (result) {
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("redirect:/createJam");
        }
    }
    //endregion

    //region game controller
    @RequestMapping("/game/{gId}")
    public String gamePage(Model model, @PathVariable long gId) {
        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);
        model.addAttribute("game", null);

        return "gameWeb";

    }
//endregion


    //region PRIVATE METHODS
    private void randomWordAction(Model model) {
        //Random generator
        model.addAttribute("topic1", randomWord.getRandomWord());
        model.addAttribute("topic2", randomWord.getRandomWord());
    }

    //endregion

}
