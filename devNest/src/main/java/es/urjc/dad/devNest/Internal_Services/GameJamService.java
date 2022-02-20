package es.urjc.dad.devNest.Internal_Services;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;
import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Database.Repositories.GamejamRepository;
import es.urjc.dad.devNest.Database.Repositories.TeamRepository;
//import es.urjc.dad.devNest.Database.Repositories.UserRepository;

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

    /*
    //region INIT
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void addJams() {
        UserEntity u = new UserEntity("pablo", "1234", "a@b.c");
        userRepository.save(u);
        gamejamRepository.save(new GamejamEntity("GGJam", u, "Perro Amarillo", "18/02/2022", "20/02/2022"));

    }
    //endregion
    */

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

    public boolean addNewTeam(long jamId, String teamName, UserEntity user)
    {
        leaveTeam(jamId, user);
        GamejamEntity gj = getJam(jamId);
        if(gj != null)
        {
            List<TeamEntity> teams = gj.getTeams();
            boolean duplicateName = false;
            for (TeamEntity teamEntity : teams) {
                if(teamEntity.getTeamName().equalsIgnoreCase(teamName))
                {
                    duplicateName = true;
                }
            }

            if(!duplicateName)
            {
                List<UserEntity> members = new ArrayList<UserEntity>();
                members.add(user);
                TeamEntity t = new TeamEntity(teamName, members, gj);
                teamRepository.save(t);
                gj.getTeams().add(t);
                gamejamRepository.save(gj);
                return true;
            }
            else
                return false;            
        }
        else
            return false;
    }
    public TeamEntity getTeam(String teamName) {
        Optional<TeamEntity> t = teamRepository.findByTeamName(teamName);

        if (t.isPresent())
            return t.get();
        else
            return null;
    }

    public boolean joinTeam(long jamId, long teamId, UserEntity user)
    {
        leaveTeam(jamId, user);
        GamejamEntity gj = getJam(jamId);
        if(gj != null)
        {  
            TeamEntity t = getTeam(teamId);
            if(t != null)
            {
                List<UserEntity> members = t.getMembers();
                members.add(user);                
                teamRepository.save(t);
                gamejamRepository.save(gj);                
                return true;
            }
            else
                return false;
        }
        else
            return false;        
    }

    public void leaveTeam(long jamId, UserEntity user)
    {
        GamejamEntity gj = getJam(jamId);
        if(gj != null)
        {
            long oldTeamId = checkIfIsInTeam(gj, user);
            if(oldTeamId != -1)
            {
                TeamEntity t = getTeam(oldTeamId);
                List<UserEntity> members = t.getMembers();
                members.remove(user);                
                teamRepository.save(t);
                gamejamRepository.save(gj); 
            }
        }
    }

    public long checkIfIsInTeam(GamejamEntity gj, UserEntity user)
    {
        List<TeamEntity> teams = gj.getTeams();
        long oldTeamId = -1;
        for (TeamEntity teamEntity : teams) {
            if(teamEntity.getMembers().contains(user))
            {
                oldTeamId = teamEntity.getId();
            }
        }
        return oldTeamId;
    }
}
