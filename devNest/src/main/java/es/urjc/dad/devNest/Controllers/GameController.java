package es.urjc.dad.devNest.Controllers;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;
import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Internal_Services.*;
import es.urjc.dad.devNest.Internal_Services.User_Services.UserService;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.security.Principal;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

/**
 * Class which contains the controllers corresponding to game actions: creating games, upload a game, adding comments,download game...
 */
@Controller
public class GameController {

    @Autowired
    private UserService userService;
    @Autowired
    private GameJamService gameJamService;
    @Autowired
    private GameService gameService;
    @Autowired
    private CommentService commentService;

    //region game controller

    /**
     * goes to a game page
     * @param model
     * @param gId game ID
     * @param request
     * @return
     */
    @RequestMapping(value = "/game/{gId}")
    public String gamePage(Model model, @PathVariable long gId, HttpServletRequest request) {

        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null)
            myUser = userService.getUser(request.getUserPrincipal().getName());

        model.addAttribute("userEntity", myUser);
        model.addAttribute("game", gameService.getGame(gId));
        return "gameWeb";
    }

    /**
     * tries to create a game associated to a team
     * @param _title
     * @param _descrition
     * @param _category
     * @param _platform
     * @param _file
     * @param tID team id
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/createGame/{tID}")
    public String createGame(@RequestParam String _title, @RequestParam String _descrition, @RequestParam String _category, @RequestParam String _platform, @RequestParam MultipartFile _file, @PathVariable long tID, HttpServletRequest request) throws IOException {
        //team by name
        TeamEntity team = gameJamService.getTeam(tID);
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
        VideogameEntity videogame = null;
        if (file != null) {
            videogame = new VideogameEntity(_title, dtf.format(now), _descrition, _category, _platform, location.toString(), file);
            result = gameService.addNewGame(videogame, tID);
        }

        if (result) {
            return "redirect:/gamejam/" + team.getGamejam().getId();
        } else {
            return "redirect:/uploadGame/" + tID;
        }
    }

    /**
     * redirects to the html where you can create a game
     * @param gId game jam id
     * @param request
     * @return
     */
    @RequestMapping("/registerGame/{gId}")
    public String goCreateGame(@PathVariable long gId, HttpServletRequest request) {
        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null)
            myUser = userService.getUser(request.getUserPrincipal().getName());

        GamejamEntity gj = gameJamService.getJam(gId);
        //check if the user is in the team before allowing him to upload a game
        long check = gameJamService.checkIfIsInTeam(gj, myUser);
        if (check != -1) {
            return "redirect:/uploadGame/" + check;
        } else
            return "redirect:/gamejam/" + gId;
    }

    /**
     * takes you to the upload game html
     * @param model
     * @param tId
     * @param request
     * @return
     */
    @RequestMapping("/uploadGame/{tId}")
    public String uploadGamePage(Model model, @PathVariable long tId, HttpServletRequest request) {
        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null)
            myUser = userService.getUser(request.getUserPrincipal().getName());

        model.addAttribute("userEntity", myUser);
        model.addAttribute("tID", tId);
        return "createGame";
    }

    /**
     * write a comment on a game
     *
     * @param gId game id
     * @param userCommentBox
     * @param request
     * @return
     */
    @RequestMapping("/game/{gId}/addComment")
    public String addComment(@PathVariable long gId, @RequestParam String userCommentBox, HttpServletRequest request) {
        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null) {
            myUser = userService.getUser(request.getUserPrincipal().getName());
            commentService.addComment(gId, myUser.getId(), userCommentBox);
        }
        return "redirect:/game/" + gId;
    }

    /**
     * write a comment on a comment
     * @param gId game id
     * @param cId comment id
     * @param userCommentBox comment tect
     * @param request
     * @return
     */
    @RequestMapping("/game/{gId}/answerComment+{cId}")
    public String answerComment(@PathVariable long gId, @PathVariable long cId, @RequestParam String userCommentBox, HttpServletRequest request) {
        UserEntity myUser = null;
        Principal up = request.getUserPrincipal();
        if (up != null) {
            myUser = userService.getUser(request.getUserPrincipal().getName());
            commentService.answerComment(gId, myUser.getId(), cId, userCommentBox);
        }
        return "redirect:/game/" + gId;
    }

    /**
     * Asks the rest to download the file corresponding to a game from the data base
     * @param gId game id
     * @param request
     * @return
     */
    @GetMapping(value = "/game/{gId}/download-game", produces = "application/zip")
    public ResponseEntity<ByteArrayResource> download(@PathVariable long gId, HttpServletRequest request) {
        return gameService.downloadGame(gId);
    }
    //endregion    
}
