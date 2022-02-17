package es.urjc.dad.devNest.Database;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

public class VideogameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String date;

    @Lob
    @Column(length = 512)
    private String description;


    private String category;

    private String platform;

    @ManyToOne
    @Column(nullable = false)
    private TeamEntity team;


    private String filePath;


    @Lob
    @JsonIgnore
    @Column(nullable = false)
    private Blob gameFile;


    public VideogameEntity() {
    }

    public VideogameEntity(String _title, Date _date, TeamEntity _team, String _filePath, Blob _gameFile) {
        title = _title;
        date = _date.toString();
        team = _team;
        filePath = _filePath;
        gameFile = _gameFile;
    }

    public VideogameEntity(String _title, Date _date, String _description, String _category, String _platform, TeamEntity _team, String _filePath, Blob _gameFile) {
        title = _title;
        date = _date.toString();
        description = _description;
        category = _category;
        platform = _platform;
        team = _team;
        filePath = _filePath;
        gameFile = _gameFile;
    }


    //region SETTERS


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setGameFile(Blob gameFile) {
        this.gameFile = gameFile;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    //endregion

    //region GETTERS

    public String getDate() {
        return date;
    }

    public Blob getGameFile() {
        return gameFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getPlatform() {
        return platform;
    }

    public String getTitle() {
        return title;
    }

    public TeamEntity getTeam() {
        return team;
    }
    //endregion


}

