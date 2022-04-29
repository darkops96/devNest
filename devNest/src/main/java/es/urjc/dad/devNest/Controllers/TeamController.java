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
import org.springframework.web.bind.annotation.*;

/**
 * Class which contains the controllers corresponding to Team actions (join a team)
 */
@Controller
public class TeamController {

    @Autowired
    private UserService userService;
    @Autowired
    private GameJamService gameJamService;
    
    private static final Log logger = LogFactory.getLog(TeamController.class);

    //region teams controller

    /**
     * joins a user to a team
     * @param gjId game jam id
     * @param tId team id
     * @param request
     * @return
     */
    @RequestMapping(value = "/gamejam/{gjId}/join+team/{tId}")
    public String joinTeam(@PathVariable long gjId, @PathVariable long tId, HttpServletRequest request) {
        logger.info("JOIN team " + tId + " in Game Jam " + gjId);
        
        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null) {
            myUser = userService.getUser(request.getUserPrincipal().getName());
        }
        if (myUser != null) {
            gameJamService.joinTeam(gjId, tId, myUser);
        }
        return "redirect:/gamejam/" + gjId;
    }
    //endregion
}
