package es.urjc.dad.devNestInternalService.Database.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Blob;

/**
 * videogame entity so the REST can read the necessary info from the data base
 */
@Entity
public class VideogameEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    private String filePath;

    @Lob
    @JsonIgnore
    private transient Blob gameFile;

    public VideogameEntity() {}


    //region GETTERS
    public Blob getGameFile() {
        return gameFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    //endregion

}

