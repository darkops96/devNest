package es.urjc.dad.devNest.Internal_Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.dad.devNest.Database.Entities.CommentEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Database.Repositories.CommentRepository;
import es.urjc.dad.devNest.Internal_Services.User_Services.UserService;

/**
 * This class manages all services and actions related to the comments
 */
@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    /**
     * Add a new comment to a game and saves it in the database
     * @param vId videogame id
     * @param uId user id
     * @param comment comment text
     * @return  true
     */
    public boolean addComment(long vId, long uId, String comment)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        VideogameEntity v = gameService.getGame(vId);
        CommentEntity c = new CommentEntity(v, userService.getUser(uId), dtf.format(now), comment);
        commentRepository.save(c);
        v.getComments().add(c);
        gameService.updateGame(v);
        return true;
    }

    /**
     * Add a new comment answering to a previous comment and saves it in the database
     * @param vId videogame id
     * @param uId user id
     * @param cId comment id
     * @param comment comment text
     * @return
     */
    public boolean answerComment(long vId, long uId, long cId, String comment)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        VideogameEntity v = gameService.getGame(vId);
        CommentEntity c = new CommentEntity(v, userService.getUser(uId), getComment(cId), dtf.format(now), comment);
        commentRepository.save(c);
        v.getComments().add(c);
        gameService.updateGame(v);
        return true;
    }

    /**
     * Get an especific comment from the database
     * @param cId id from the comment
     * @return the comment
     */
    public CommentEntity getComment(long cId)
    {
        Optional<CommentEntity> c = commentRepository.findById(cId);
        if(c.isPresent())
            return c.get();
        else
            return null;
    }

}
