package es.urjc.dad.devNest.Controllers;

import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Internal_Services.AsyncEmailService;
import es.urjc.dad.devNest.Internal_Services.User_Services.UserService;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

/**
 * Class which contains the controllers corresponding to user actions such as login, register, travel through
 * the different pages
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AsyncEmailService asyncEmailService;

    //region login controller

    /**
     * goes to the login html, but if it has failed to login previously, it goes to the loguin error controller
     *
     * @param model
     * @param error
     * @param request
     * @return
     */
    @GetMapping(value = "/login")
    public String login(Model model, @RequestParam(name = "error", required = false) boolean error, HttpServletRequest request) {
        model.addAttribute("error", error);
        return "loginWeb";
    }

    /**
     * Enables in the html an indication that tells you that you have failed to login
     *
     * @param redirectAttributes
     * @param request
     * @return redirects to login html
     */
    @GetMapping(value = "/login-error")
    public String failedLogin(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addAttribute("error", true);
        return "redirect:/login";
    }
    //endregion

    //region register user controller

    /**
     * takes to the register html
     *
     * @param request
     * @return
     */
    @GetMapping("/register")
    public String goToRegister(HttpServletRequest request) {
        return "registerWeb";
    }

    /**
     * Register a user in the database, log in and sends a registration email
     *
     * @param username  of the user
     * @param psw       password
     * @param email     email
     * @param pswRepeat again the password to ensuere the user wrote it correctly
     * @param request
     * @return redirects to home page or register depending of the result of the registration
     */
    @PostMapping(value = "/registerUser")
    public String register(@RequestParam String username, @RequestParam String psw, @RequestParam String email, @RequestParam String pswRepeat, HttpServletRequest request) {
        //check the passwors match
        if (psw.equals(pswRepeat)) {
            //attempts to register a user
            boolean result = userService.register(username, psw, email);
            if (result) {
                //logs in the user
                userService.authAfterRegister(request, username, psw);
                try {
                    //send the registration email
                    asyncEmailService.sendRegisterEmail(username, email);
                } catch (RestClientException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return "redirect:/";
            } else {
                return "redirect:/register";
            }
        } else {
            return "redirect:/register";
        }
    }
    //endregion

    //region my profile controller

    /**
     * goes to the profile page and sets all the information needed
     *
     * @param model
     * @param uId
     * @param request
     * @return
     */
    @RequestMapping("/profile/{uId}")
    public String goToProfile(Model model, @PathVariable long uId, HttpServletRequest request) {
        UserEntity user = userService.getUser(uId);

        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null) {
            myUser = userService.getUser(request.getUserPrincipal().getName());
        }

        model.addAttribute("myUserEntity", myUser);

        if (myUser != null && myUser.getId() == user.getId())
            model.addAttribute("myProfile", true);
        else
            model.addAttribute("myProfile", false);

        model.addAttribute("userEntity", user);
        model.addAttribute("videogame", userService.getGames(userService.getUserTeams(uId)));
        return "profileWeb";
    }

    /**
     * Downloads the picture from the database and puts it in the html so it can be shown when someone enters the profile
     *
     * @param id of the user
     * @return
     * @throws SQLException
     * @throws MalformedURLException
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException, MalformedURLException {
        UserEntity myUser = userService.getUser(id);
        if (myUser.getProfilePicture() != null) {
            InputStreamResource file = new InputStreamResource(
                    myUser.getPPictureFile().getBinaryStream());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/png", "image/jpg", "image/jpeg", "image/gif")
                    .contentLength(myUser.getPPictureFile().length())
                    .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * edit the user profile info meaning that it can change the description, and the profile picture
     *
     * @param description
     * @param myfile
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/editProfile")
    public String updateProfile(@RequestParam String description, @RequestParam MultipartFile myfile, HttpServletRequest request) throws IOException {
        UserEntity user = null;
        Principal up = request.getUserPrincipal();
        if (up != null) {
            user = userService.getUser(request.getUserPrincipal().getName());
        }
        if (user != null) {
            user.setDescription(description);
            URI location = fromCurrentRequest().build().toUri();
            if (!myfile.isEmpty()) {
                user.setProfilePicture(location.toString());
                user.setPPictureFile(BlobProxy.generateProxy(myfile.getInputStream(), myfile.getSize()));
            }
            userService.updateUser(user);
        }
        return "redirect:/profile/" + user.getId();
    }

    //endregion  
}
