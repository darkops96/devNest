package es.urjc.dad.devNest.Internal_Services;

import java.net.URISyntaxException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;
import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Database.Repositories.GamejamRepository;
import es.urjc.dad.devNest.Database.Repositories.TeamRepository;


/**
 * this class is responsible for the management and services related to gamejams
 */
@Service
public class GameJamService {
    @Autowired
    private GamejamRepository gamejamRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private AsyncEmailService asyncEmailService;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * get all jams in the database and updates the internal list if necessay
     *
     * @return a list with all the jams
     */
    public List<GamejamEntity> getAllJams() {
        List<GamejamEntity> allJams = gamejamRepository.findAll();
        for (GamejamEntity gameJam : allJams) {
            cacheAllJams(gameJam);
        }
        return allJams;
    }

    @Cacheable(value = "gamejams", key = "#gamejamEntity.id")
    public GamejamEntity cacheAllJams(GamejamEntity gamejamEntity) {
        return gamejamEntity;
    }

    /**
     * Add a new jam to the database
     *
     * @param _name
     * @param description
     * @param _userEntity creator of the jam
     * @param _topic
     * @param _startDate
     * @param _endDate
     * @return true if the jam was created succesfully
     */
    public boolean addNewJam(String _name, String description, UserEntity _userEntity, String _topic, String _startDate, String _endDate) {
        GamejamEntity newJam = new GamejamEntity(_name, _userEntity, description, _topic, _startDate, _endDate);
        Optional<GamejamEntity> u = gamejamRepository.findById(newJam.getId());
        if (!u.isPresent()) {
            gamejamRepository.save(newJam);
            return true;
        } else
            return false;
    }

    /**
     * if the jam exists it deletes it from the database and indicates that the internal list must be updated
     *
     * @param id of the jam that needs to be deleted
     */
    @Transactional
    public void deleteJam(long id) {
        Optional<GamejamEntity> jam = gamejamRepository.findById(id);
        if (jam.isPresent()) {            
            Session session = entityManager.unwrap(Session.class);
            session.merge(jam);
            teamRepository.deleteAll(jam.get().getTeams());
            gamejamRepository.delete(jam.get());
        }
    }

    /**
     * Get an especific jam
     *
     * @param id
     * @return
     */
    public GamejamEntity getJam(long id) {
        Optional<GamejamEntity> u = gamejamRepository.findById(id);
        if (u.isPresent())
            return u.get();
        else
            return null;
    }

    /**
     * Get an especific team
     *
     * @param id
     * @return
     */
    public TeamEntity getTeam(long id) {
        Optional<TeamEntity> t = teamRepository.findById(id);
        if (t.isPresent())
            return t.get();
        else
            return null;
    }

    /**
     * Get the game corresponding to a team
     *
     * @param id
     * @return
     */
    public Blob getTeamGame(long id) {
        TeamEntity t = getTeam(id);
        if (t != null) {
            VideogameEntity v = t.getVideogame();
            if (v != null)
                return v.getGameFile();
        }
        return null;
    }

    /**
     * Add a new team to the database and joins the current user to the game
     *
     * @param jamId
     * @param teamName
     * @param user
     * @return
     */
    public boolean addNewTeam(long jamId, String teamName, UserEntity user) {
        leaveTeam(jamId, user);
        GamejamEntity gj = getJam(jamId);
        if (gj != null) {
            List<TeamEntity> teams = gj.getTeams();
            boolean duplicateName = false;
            for (TeamEntity teamEntity : teams) {
                if (teamEntity.getTeamName().equalsIgnoreCase(teamName)) {
                    duplicateName = true;
                }
            }

            if (!duplicateName) {
                List<UserEntity> members = new ArrayList<UserEntity>();
                members.add(user);
                TeamEntity t = new TeamEntity(teamName, members, gj);
                teamRepository.save(t);
                gj.getTeams().add(t);
                gamejamRepository.save(gj);

                try {
                    asyncEmailService.sendJoinTeam(user.getAlias(), user.getEmail(), teamName);
                } catch (RestClientException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            } else
                return false;
        } else
            return false;
    }

    /**
     * get a team by its name from the database
     *
     * @param teamName
     * @return
     */
    public TeamEntity getTeam(String teamName) {
        Optional<TeamEntity> t = teamRepository.findByTeamName(teamName);

        if (t.isPresent())
            return t.get();
        else
            return null;
    }

    /**
     * Join a new team and leave the old if necessary
     *
     * @param jamId
     * @param teamId
     * @param user
     * @return true if it could successfully join the team
     */
    public boolean joinTeam(long jamId, long teamId, UserEntity user) {
        GamejamEntity gj = getJam(jamId);
        long oldTeamId = checkIfIsInTeam(gj, user);

        if (oldTeamId != teamId) {
            leaveTeam(jamId, user);
            if (gj != null) {
                TeamEntity t = getTeam(teamId);
                if (t != null) {
                    if (teamId != oldTeamId) {
                        List<UserEntity> members = t.getMembers();
                        members.add(user);
                        teamRepository.save(t);
                        gamejamRepository.save(gj);

                        try {
                            asyncEmailService.sendJoinTeam(user.getAlias(), user.getEmail(), t.getTeamName());
                        } catch (RestClientException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        return true;
                    } else
                        return false;
                } else
                    return false;
            } else
                return false;
        } else
            return false;
    }

    /**
     * Leave a team if it was in one
     *
     * @param jamId
     * @param user
     */
    public void leaveTeam(long jamId, UserEntity user) {
        GamejamEntity gj = getJam(jamId);
        if (gj != null) {
            long oldTeamId = checkIfIsInTeam(gj, user);
            if (oldTeamId != -1) {
                TeamEntity t = getTeam(oldTeamId);
                List<UserEntity> members = t.getMembers();

                if (members.size() == 1) {
                    gj.getTeams().remove(t);
                    teamRepository.deleteById(t.getId());
                } else {
                    members.remove(user);
                    teamRepository.save(t);
                }
                gamejamRepository.save(gj);
            }
        }
    }

    /**
     * checks if the current user is in a team
     *
     * @param gj
     * @param user
     * @return
     */
    public long checkIfIsInTeam(GamejamEntity gj, UserEntity user) {
        List<TeamEntity> teams = gj.getTeams();
        for (TeamEntity teamEntity : teams) {
            for (UserEntity u : teamEntity.getMembers()) {
                if (u.getId() == user.getId()) {
                    return teamEntity.getId();
                }
            }
        }
        return -1;
    }

    /**
     * looks for all the teams with no members and deletes them
     *
     * @param jamId
     */
    public void deleteEmptyTeams(long jamId) {
        GamejamEntity gj = getJam(jamId);
        if (gj != null) {
            List<TeamEntity> teams = gj.getTeams();
            List<Long> emptyTeams = new ArrayList<Long>();
            if (!teams.isEmpty()) {
                for (TeamEntity teamEntity : teams) {
                    if (teamEntity.getMembers().size() == 0) {
                        emptyTeams.add(teamEntity.getId());
                    }
                }
                teamRepository.deleteAllById(emptyTeams);
                gamejamRepository.save(gj);
            }
        }
    }
}
