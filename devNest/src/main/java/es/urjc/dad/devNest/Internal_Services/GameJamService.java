package es.urjc.dad.devNest.Internal_Services;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;
import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import es.urjc.dad.devNest.Database.Repositories.GamejamRepository;
import es.urjc.dad.devNest.Database.Repositories.TeamRepository;

/**
 * Class responsible of the management of Gam Jams. It provides service to control the accions with the gamejams
 */
@Service
public class GameJamService {
    @Autowired
    private GamejamRepository gamejamRepository;
    @Autowired
    private TeamRepository teamRepository;

    private List<GamejamEntity> allJams;
    private boolean needsUpdate;


    public GameJamService() {
        //incates that the copy in memory of the jam list is outdated
        needsUpdate = true;
    }

    /**
     * updates the list of current jams with the jams present in the database
     */
    public void refreshJamList() {
        allJams = gamejamRepository.findAll();
        needsUpdate = false; //indicates that the jam list is updated
    }

    public List<GamejamEntity> getAllJams() {
        if (needsUpdate)
            refreshJamList();

        return allJams;
    }

    public boolean addNewJam(String _name, String description, UserEntity _userEntity, String _topic, String _startDate, String _endDate) {
        GamejamEntity newJam = new GamejamEntity(_name, _userEntity, description, _topic, _startDate, _endDate);
        Optional<GamejamEntity> u = gamejamRepository.findById(newJam.getId());
        if (!u.isPresent()) {
            gamejamRepository.save(newJam);
            needsUpdate = true;
            return true;
        } else
            return false;
    }

    public void sendRegisterJam(String username, String email, String jam) throws RestClientException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8080/emails/create-jam/");

        List<String> data = new ArrayList<>(3);
        data.add(username);
        data.add(email);
        data.add(jam);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List> requestEntity = new HttpEntity<>(data, headers);

        restTemplate.postForEntity(url, requestEntity, String.class);
    }

    public void deleteJam(long id) {
        Optional<GamejamEntity> jam = gamejamRepository.findById(id);
        if (jam.isPresent()) {
            teamRepository.deleteAll(jam.get().getTeams());
            gamejamRepository.delete(jam.get());
            needsUpdate = true;
        }
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
                needsUpdate = true;

                try {
                    sendJoinTeam(user.getAlias(), user.getEmail(), teamName);
                } catch (RestClientException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ;
                return true;
            } else
                return false;
        } else
            return false;
    }

    private void sendJoinTeam(String username, String email, String team) throws RestClientException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8080/emails/join-team/");

        List<String> data = new ArrayList<>(3);
        data.add(username);
        data.add(email);
        data.add(team);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List> requestEntity = new HttpEntity<>(data, headers);

        restTemplate.postForEntity(url, requestEntity, String.class);
    }

    public TeamEntity getTeam(String teamName) {
        Optional<TeamEntity> t = teamRepository.findByTeamName(teamName);

        if (t.isPresent())
            return t.get();
        else
            return null;
    }

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
                        needsUpdate = true;

                        try {
                            sendJoinTeam(user.getAlias(), user.getEmail(), t.getTeamName());
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
                needsUpdate = true;
            }
        }
    }

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

    public void deleteEmptyTeams(long jamId) {
        GamejamEntity gj = getJam(jamId);
        if (gj != null) {
            List<TeamEntity> teams = gj.getTeams();
            List<Long> emptyTeams = new ArrayList<Long>();
            for (TeamEntity teamEntity : teams) {
                if (teamEntity.getMembers().size() == 0) {
                    emptyTeams.add(teamEntity.getId());
                }
            }
            teamRepository.deleteAllById(emptyTeams);
            gamejamRepository.save(gj);
            needsUpdate = true;
        }
    }
}
