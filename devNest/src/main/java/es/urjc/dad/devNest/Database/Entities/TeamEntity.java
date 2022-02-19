package es.urjc.dad.devNest.Database.Entities;

import java.util.List;

import javax.persistence.*;


@Entity
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String teamName;

    @ManyToMany
    private List<UserEntity> members;

    @OneToOne(cascade = CascadeType.ALL)
    private VideogameEntity videogame;

    public TeamEntity() {
    }

    public TeamEntity(String _name, List<UserEntity> _users, VideogameEntity game) {
        super();
        teamName = _name;
        members = _users;
        videogame = game;
    }


    public TeamEntity(String _name, List<UserEntity> _users) {
        super();
        teamName = _name;
        members = _users;
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

    @Override
	public String toString() {
		return "Team [name=" + teamName + "]";
	}
}
