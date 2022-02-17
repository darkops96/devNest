package es.urjc.dad.devNest.Database;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;



@Entity
public class TeamEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String teamName;

    @ManyToMany
    private List<UserEntity> members;

    public TeamEntity() {}

    public long getId()
    {
        return id;
    }

    public void setId(long _id)
    {
        id = _id;
    }

    public String getTeamName()
    {
        return teamName;
    }

    public void setTeamName(String _teamName)
    {
        teamName = _teamName;
    }

    public List<UserEntity> getMembers()
    {
        return members;
    }

    public void setMembers(List<UserEntity> _members)
    {
        members = _members;
    }
}
