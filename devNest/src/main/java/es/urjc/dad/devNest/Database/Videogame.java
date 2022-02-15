package es.urjc.dad.devNest.Database;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Videogame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (nullable = false)
    private String title;


    @Column(nullable = false)
    private String date;

    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String platform;

    //hay que meter el fichero pero ªªªªªª



    //region SETTERS


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

    //endregion

    //region GETTERS

    public String getDate() {
        return date;
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
    //endregion


}

