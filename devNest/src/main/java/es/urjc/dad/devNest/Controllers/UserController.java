package es.urjc.dad.devNest.Controllers;

import es.urjc.dad.devNest.Database.Entities.UserEntity;
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
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //region login controller
    @GetMapping(value = "/login")
    public String login(Model model, HttpServletRequest request) {
        return "loginWeb";
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
        model.addAttribute("videogame", userService.getGames(userService.getUserTeams(uId)));
        return "profileWeb";
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
}
