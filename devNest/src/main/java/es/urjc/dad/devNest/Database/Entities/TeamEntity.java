package es.urjc.dad.devNest.Database.Entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
public class TeamEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String teamName;

    @ManyToMany
    private List<UserEntity> members;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private VideogameEntity videogame;

    @ManyToOne
    private GamejamEntity gamejam;

    public TeamEntity() {
    }

    public TeamEntity(String _name, List<UserEntity> _users, GamejamEntity _gamejam, VideogameEntity game) {
        super();
        teamName = _name;
        members = _users;
        gamejam = _gamejam;
        videogame = game;
    }


    public TeamEntity(String _name, List<UserEntity> _users, GamejamEntity _gamejam) {
        super();
        teamName = _name;
        members = _users;
        gamejam = _gamejam;
    }


    public long getId() {
        return id;
    }

    public void setId(long _id) {
        id = _id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String _teamName) {
        teamName = _teamName;
    }

    public List<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(List<UserEntity> _members) {
        members = _members;
    }

    public VideogameEntity getVideogame() {
        return videogame;
    }

    public void setVideogame(VideogameEntity _videogame) {
        this.videogame = _videogame;
    }

    public GamejamEntity getGamejam() {
        return gamejam;
    }

    public void setGamejam(GamejamEntity _gamejam) {
        this.gamejam = _gamejam;
    }

    @Override
    public String toString() {
        return "Team [name=" + teamName + "]";
    }
}
