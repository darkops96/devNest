package es.urjc.dad.devNest.Internal_Services;

import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;
import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Database.Repositories.GamejamRepository;
import es.urjc.dad.devNest.Database.Repositories.TeamRepository;
import es.urjc.dad.devNest.Database.Repositories.UserRepository;

@Service
public class GameJamService {
    @Autowired
    private GamejamRepository gamejamRepository;
    @Autowired
    private TeamRepository teamRepository;

    private List<GamejamEntity> allJams;
    private boolean needsUpdate;

    public GameJamService() {
        needsUpdate = true;
    }

    //region INIT
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void addJams() {
        //UserEntity u = new UserEntity("pablo", "1234", "a@b.c");
        //userRepository.save(u);
        //  gamejamRepository.save(new GamejamEntity("GGJam", u, "Perro Amarillo", "18/02/2022", "20/02/2022"));

    }
    //endregion

    public void refreshJamList() {
        allJams = gamejamRepository.findAll();
        needsUpdate = false;
    }

    public List<GamejamEntity> getAllJams() {
        if (needsUpdate)
            refreshJamList();

        return allJams;
    }

    public boolean addNewJam(String _name,String description, UserEntity _userEntity, String _topic, String _startDate, String _endDate) {
        GamejamEntity newJam = new GamejamEntity(_name, _userEntity, description,_topic, _startDate, _endDate);
        //(String _name, UserEntity _userEntity,String _description, String _topic, String _startDate, String _endDate, TeamEntity _winner)
        Optional<GamejamEntity> u = gamejamRepository.findById(newJam.getId());

        if (!u.isPresent()) {
            gamejamRepository.save(newJam);
            needsUpdate = true;
            return true;
        } else
            return false;
    }

    public GamejamEntity getJam(long id) {
        Optional<GamejamEntity> u = gamejamRepository.findById(id);

        if (u.isPresent())
            return u.get();
        else
            return null;
    }

    public TeamEntity getTeam(long id) {
        Optional<TeamEntity> t = teamRepository.findById(id);

        if (t.isPresent())
            return t.get();
        else
            return null;
    }

    public Blob getTeamGame(long id) {
        TeamEntity t = getTeam(id);
        if (t != null) {
            VideogameEntity v = t.getVideogame();
            if (v != null)
                return v.getGameFile();
        }
        return null;
    }
}
