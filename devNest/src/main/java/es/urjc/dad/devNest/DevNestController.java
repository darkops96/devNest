package es.urjc.dad.devNest;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;
import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Internal_Services.*;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
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
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException, MalformedURLException {
        UserEntity myUser = userService.getUser(id);
        if (myUser.getProfilePicture() != null) {
            InputStreamResource file = new InputStreamResource(
                    myUser.getPPictureFile().getBinaryStream());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/png","image/jpg","image/jpeg","image/gif")
                    .contentLength(myUser.getPPictureFile().length())
                    .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/editProfile")
    public ModelAndView updateProfile(@RequestParam String description, @RequestParam MultipartFile myfile) throws IOException {
        UserEntity user = userService.getMyUser();
        user.setDescription(description);
        URI location = fromCurrentRequest().build().toUri();
        if (!myfile.isEmpty()) {
            user.setProfilePicture(location.toString());
            user.setPPictureFile(BlobProxy.generateProxy(myfile.getInputStream(), myfile.getSize()));
        }
        userService.updateUser(user);
        return new ModelAndView("redirect:/myProfile");
    }

    //endregion

    //region gamejam controller
    @RequestMapping("/gamejam/{gjId}")
    public String jamPage(Model model, @PathVariable long gjId) {
        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);

        gameJamService.deleteEmptyTeams(gjId);
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
        model.addAttribute("game", gameService.getGame(gId));
        return "gameWeb";
    }

    @RequestMapping(value = "/createGame/{tName}")
    public ModelAndView createGame(@RequestParam String _title, @RequestParam String _descrition, @RequestParam String _category, @RequestParam String _platform, @RequestParam MultipartFile _file, @PathVariable String tName) throws IOException {
        //team by name
        TeamEntity team = gameJamService.getTeam(tName);
        //current date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        //file
        Blob file = null;
        URI location = fromCurrentRequest().build().toUri();
        if (!_file.isEmpty()) {
            file = BlobProxy.generateProxy(_file.getInputStream(), _file.getSize());
        }

        boolean result = false;
        if(file != null)
        {
            VideogameEntity videogame = new VideogameEntity(_title, dtf.format(now), _descrition, _category, _platform, team, location.toString(), file);
            result = gameService.addNewGame(videogame, tName);
        }
        
        if (result) {
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("redirect:/createGame/"+tName);
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

    @RequestMapping("/registerGame/{gId}")
    public ModelAndView goCreateGame(@PathVariable long gId) {
        UserEntity myUser = userService.getMyUser();
        GamejamEntity gj = gameJamService.getJam(gId);
        
        long check = gameJamService.checkIfIsInTeam(gj, myUser);
        if(check != -1)
        {
            return new ModelAndView("redirect:/uploadGame/"+check);
        }
        else
            return new ModelAndView("redirect:/gamejam/" + gId);        
    }

    @RequestMapping("/uploadGame/{tId}")
    public String uploadGamePage(Model model, @PathVariable long tId)
    {
        UserEntity myUser = userService.getMyUser();
        model.addAttribute("userEntity", myUser);        
        model.addAttribute("tName", gameJamService.getTeam(tId).getTeamName());
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
