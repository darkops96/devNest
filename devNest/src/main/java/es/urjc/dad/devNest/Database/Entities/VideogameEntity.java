package es.urjc.dad.devNest.Database.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

@Entity
public class VideogameEntity implements Serializable {
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

    private String filePath;
    
    @Lob
    @JsonIgnore
    private Blob gameFile;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "videogame", orphanRemoval = true)
    private List<CommentEntity> comments;


    public VideogameEntity() {
    }

    public VideogameEntity(String _title, String _date, String _filePath, Blob _gameFile) {
        super();
        title = _title;
        date = _date;
        filePath = _filePath;
        gameFile = _gameFile;
    }

    public VideogameEntity(String _title, String _date, String _description, String _category, String _platform, String _filePath, Blob _gameFile) {
        super();
        title = _title;
        date = _date;
        description = _description;
        category = _category;
        platform = _platform;
        filePath = _filePath;
        gameFile = _gameFile;

    }

    public VideogameEntity(String _title, String _date, String _description, String _category, String _platform, Blob _gameFile) {
        super();
        title = _title;
        date = _date;
        description = _description;
        category = _category;
        platform = _platform;
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

    public void setComments(List<CommentEntity> _comments) {
        this.comments = _comments;
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

    public List<CommentEntity> getComments() {
        return comments;
    }

    //endregion

    @Override
    public String toString() {
        return "Videogame [title=" + title + ", date=" + date + "]";
    }

    @Override
    public boolean equals(Object obj) {
        // self check
        if (this == obj)
            return true;
        // null check
        if (obj == null)
            return false;
        // type check and cast
        if (getClass() != obj.getClass())
            return false;        
        GamejamEntity gamejamEntity = (GamejamEntity) obj;
        return this.getId() == gamejamEntity.getId();
    }

    @Override
    public int hashCode() {
        return (int) getId() * getTitle().hashCode();
    }
}

