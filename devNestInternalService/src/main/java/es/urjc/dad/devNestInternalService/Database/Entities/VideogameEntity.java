package es.urjc.dad.devNestInternalService.Database.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Blob;

@Entity
public class VideogameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String filePath;

    @Lob
    @JsonIgnore
    @Column(nullable = false)
    private Blob gameFile;

    public VideogameEntity() {
    }


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
    
    //endregion

}

