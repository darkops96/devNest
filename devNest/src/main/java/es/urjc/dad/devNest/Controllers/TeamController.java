package es.urjc.dad.devNest.Controllers;

import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Internal_Services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TeamController {

    @Autowired
    private UserService userService;
    @Autowired
    private GameJamService gameJamService;

    //region teams controller
    @RequestMapping(value = "/gamejam/{gjId}/join+team/{tId}")
    public ModelAndView joinTeam(@PathVariable long gjId, @PathVariable long tId) {
        UserEntity myUser = userService.getMyUser();
        if (myUser != null) {
            gameJamService.joinTeam(gjId, tId, myUser);
        }
        return new ModelAndView("redirect:/gamejam/" + gjId);
    }  
    //endregion
}
