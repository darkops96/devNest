package es.urjc.dad.devNest.Controllers;

import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Internal_Services.*;
import es.urjc.dad.devNest.Internal_Services.User_Services.UserService;

import java.net.URISyntaxException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

/**
 * Class which contains the controllers corresponding to Game jam actions: register a jam, go to jam page...
 */
@Controller
public class GameJamController {

    @Autowired
    private UserService userService;
    @Autowired
    private GameJamService gameJamService;
    @Autowired
    private RandomWordService randomWord;
    @Autowired
    private AsyncEmailService asyncEmailService;

    private static final Log logger = LogFactory.getLog(GameJamController.class);

    //region gamejam controller

    /**
     * goes to a jam html and deletes all the jams that are empty
     *
     * @param model
     * @param gjId
     * @param request
     * @return
     */
    @RequestMapping("/gamejam/{gjId}")
    public String jamPage(Model model, @PathVariable long gjId, HttpServletRequest request) {
        logger.info("GET Game Jam " + gjId + " page");

        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null)
            myUser = userService.getUser(request.getUserPrincipal().getName());

        model.addAttribute("userEntity", myUser);
        //delete all teams that are empty
        gameJamService.deleteEmptyTeams(gjId);
        model.addAttribute("gamejam", gameJamService.getJam(gjId));
        return "gameJamWeb";
    }

    /**
     * Registers a new team to a jam and automatically the user joins the team
     *
     * @param gjId game jam id
     * @param teamname name of the new team
     * @param request
     * @return
     */
    @RequestMapping(value = "/gamejam/{gjId}/register+team")
    public String registerTeam(@PathVariable long gjId, @RequestParam String teamname, HttpServletRequest request) {
        logger.info("POST team " + teamname + " for Game Jam " + gjId);

        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null) {
            myUser = userService.getUser(request.getUserPrincipal().getName());
        }
        if (myUser != null) {
            gameJamService.addNewTeam(gjId, teamname, myUser);
        }
        return "redirect:/gamejam/" + gjId;
    }
    //endregion

    //region register jam controller

    /**
     * redirects to the html to create a new game jam and suggests 2 random topics
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/registerJam")
    public String goToOrganizeJam(Model model, HttpServletRequest request) {
        logger.info("GET organize Game Jam page");

        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null)
            myUser = userService.getUser(request.getUserPrincipal().getName());

        model.addAttribute("userEntity", myUser);

        randomWordAction(model);
        return "createJam";
    }

    /**
     * tries to create a jam and if it was succesfully created it sends a confirmation email
     * @param jamName
     * @param description
     * @param topic
     * @param sDate
     * @param eDate
     * @param request
     * @return
     */
    @RequestMapping(value = "/registerGameJam")
    public String createAJam(@RequestParam String jamName, @RequestParam String description, @RequestParam String topic, @RequestParam String sDate, @RequestParam String eDate, HttpServletRequest request) {
        logger.info("POST Game Jam " + jamName);
        
        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null)
            myUser = userService.getUser(request.getUserPrincipal().getName());

        boolean result = gameJamService.addNewJam(jamName, description, myUser, topic, sDate, eDate);
        if (result) {
            try {
                asyncEmailService.sendRegisterJam(myUser.getAlias(), myUser.getEmail(), jamName);
            } catch (RestClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "redirect:/";
        } else {
            return "redirect:/createJam";
        }
    }
    //endregion 

    //region PRIVATE METHODS
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
