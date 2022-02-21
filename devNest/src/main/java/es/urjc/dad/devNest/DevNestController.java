package es.urjc.dad.devNest;

import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Internal_Services.*;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@Controller
public class DevNestController {

    @Autowired
    private UserService userService;
    @Autowired
    private GameJamService gameJamService;
    @Autowired
    private GameService gameService;
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
        return "profileWeb";
    }

    @RequestMapping("/profile/{uId}")
    public String goToProfile(Model model, @PathVariable long uId) {
        UserEntity user = userService.getUser(uId);
        model.addAttribute("userEntity", user);
        model.addAttribute("profilePicture", user.getProfilePicture());
        model.addAttribute("videogame", userService.getGames(userService.getUserTeams(uId)));
        return "profile";
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable long id)
            throws SQLException, MalformedURLException {

        UserEntity user = userService.getMyUser();
        Path imagePath = Path.of(user.getProfilePicture());
        Resource image = (Resource) new UrlResource(imagePath.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(image);
    }

    @RequestMapping(value = "/editProfile")
    public ModelAndView goToProfile(@RequestParam String description, @RequestParam MultipartFile myfile) throws IOException {
        UserEntity user = userService.getMyUser();
        user.setDescription(description);
        URI location = fromCurrentRequest().build().toUri();
        user.setProfilePicture(location.toString());
        if (myfile != null) user.setPPictureFile(BlobProxy.generateProxy(myfile.getInputStream(), myfile.getSize()));
        userService.updateUser(user);
        return new ModelAndView("redirect:/myProfile");
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


    @RequestMapping(value = "/gamejam/{gjId}/register+team")
    public ModelAndView registerTeam(@PathVariable long gjId, @RequestParam String teamname) {
        UserEntity myUser = userService.getMyUser();
        if (myUser != null) {
            gameJamService.addNewTeam(gjId, teamname, myUser);
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
    @RequestMapping(value = "/game/{gId}")
    public String gamePage(Model model, @PathVariable long gId, @RequestParam String comment) {

        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);
        model.addAttribute("game", null);
        return "gameWeb";
    }

    @RequestMapping(value = "/createGame")
    public ModelAndView createGame(@RequestParam String _title, @RequestParam String _descrition, @RequestParam String _category, @RequestParam String _platform, @RequestParam String _teamName, @RequestParam MultipartFile _file) throws IOException {
        //team by name
        TeamEntity team = gameJamService.getTeam(_teamName);
        //current date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        //file
        Blob file = BlobProxy.generateProxy(_file.getInputStream(), _file.getSize());
        URI location = fromCurrentRequest().build().toUri();

        VideogameEntity videogame = new VideogameEntity(_title, dtf.format(now), _descrition, _category, _platform, team, location.toString(), file);

        boolean result = gameService.addNewGame(videogame);
        if (result) {
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("redirect:/createGame");
        }
    }


    @RequestMapping(value = "/gamejam/{gjId}/join+team/{tId}")
    public ModelAndView joinTeam(@PathVariable long gjId, @PathVariable long tId) {
        UserEntity myUser = userService.getMyUser();
        if (myUser != null) {
            gameJamService.joinTeam(gjId, tId, myUser);
        }
        return new ModelAndView("redirect:/gamejam/" + gjId);
    }

    @GetMapping("/registerGame")
    public String goCreateGame(Model model) {
        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);
        return "createGame";
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
