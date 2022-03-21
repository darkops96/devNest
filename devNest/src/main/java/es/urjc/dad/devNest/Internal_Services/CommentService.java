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

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

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

    public CommentEntity getComment(long cId)
    {
        Optional<CommentEntity> c = commentRepository.findById(cId);
        if(c.isPresent())
            return c.get();
        else
            return null;
    }

}
