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


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AsyncEmailService asyncEmailService;

    //region login controller
    @GetMapping(value = "/login")
    public String login(Model model, @RequestParam(name = "error", required = false) boolean error, HttpServletRequest request) {   
        model.addAttribute("error", error);     
        return "loginWeb";
    }

    @GetMapping(value = "/login-error")
    public String failedLogin(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addAttribute("error", true);
        return "redirect:/login";
    }
    //endregion

    //region register user controller
    @GetMapping("/register")
    public String goToRegister(HttpServletRequest request) {
        return "registerWeb";
    }

    @PostMapping(value = "/registerUser")
    public String register(@RequestParam String username, @RequestParam String psw, @RequestParam String email, @RequestParam String pswRepeat, HttpServletRequest request) {
        if (psw.equals(pswRepeat)) {
            boolean result = userService.register(username, psw, email);
            if (result) {
                userService.authAfterRegister(request, username, psw);
                try {
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
    @RequestMapping("/profile/{uId}")
    public String goToProfile(Model model, @PathVariable long uId, HttpServletRequest request) {
        UserEntity user = userService.getUser(uId);

        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();  
        if(up != null)
        {
            myUser = userService.getUser(request.getUserPrincipal().getName());
        }

        model.addAttribute("myUserEntity", myUser);

        if(myUser != null && myUser.getId() == user.getId())
            model.addAttribute("myProfile", true);
        else     
            model.addAttribute("myProfile", false);

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
    public String updateProfile(@RequestParam String description, @RequestParam MultipartFile myfile, HttpServletRequest request) throws IOException {
        UserEntity user = null;
        Principal up = request.getUserPrincipal();  
        if(up != null)
        {
            user = userService.getUser(request.getUserPrincipal().getName());
        }
        if(user != null)
        {
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
