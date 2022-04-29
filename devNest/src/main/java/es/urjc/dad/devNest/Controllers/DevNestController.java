package es.urjc.dad.devNest.Controllers;

import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Internal_Services.*;
import es.urjc.dad.devNest.Internal_Services.User_Services.UserService;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Class which contains the controllers corresponding to the home page and admin funtions (delete a jam button)
 */
@Controller
public class DevNestController {

    @Autowired
    private UserService userService;
    @Autowired
    private GameJamService gameJamService;
    @Autowired
    private RandomWordService randomWord;

    private static final Log logger = LogFactory.getLog(DevNestController.class);

    //region initial web controller

    /**
     * Loads the home xml, asks the internal service for random words and if the user is the admin, then it enables
     * in the html the delete jam button
     *
     * @param model
     * @param request cookie
     * @return initial web html
     */
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        logger.info("GET home page");
        //Random word generator
        randomWordAction(model);
        //list of jams
        model.addAttribute("gamejams", gameJamService.getAllJams());
        //current user
        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null)
            myUser = userService.getUser(request.getUserPrincipal().getName());
        model.addAttribute("userEntity", myUser);
        //we give the html the role so if it is user the delete jam button appears
        model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));

        return "initialWeb";
    }

    /**
     * Takes you to a page that only the admin has access to. At that page, the admin can delete game Jams.
     *
     * @param model
     * @param request
     * @return the html where the admin can delete jams
     */
    @GetMapping("/admin")
    public String adminPage(Model model, HttpServletRequest request) {
        logger.info("GET admin page");
        model.addAttribute("gamejams", gameJamService.getAllJams());

        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null)
            myUser = userService.getUser(request.getUserPrincipal().getName());
        model.addAttribute("userEntity", myUser);

        return "adminWeb";
    }

    /**
     * Calls the service to delete a jam
     *
     * @param id of the jam that has to be deleted
     * @return redirects to the same page it was
     */
    @GetMapping("/admin/delete-jam/{id}")
    public String deleteJam(@PathVariable long id) {
        logger.info("DELETE Game Jam " + id);
        gameJamService.deleteJam(id);
        return "redirect:/admin";
    }
    //endregion  

    //region PRIVATE METHODS

    /**
     * Auxiliar method that requests for random words to the service and places them in the html
     *
     * @param model
     */
    private void randomWordAction(Model model) {
        //Random generator
        int i = 1;
        for (String topic : randomWord.getRandomWord()) {
            model.addAttribute("topic" + i, topic);
            i++;
        }
    }
    //endregion
}
