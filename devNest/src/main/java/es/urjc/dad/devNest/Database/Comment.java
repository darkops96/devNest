package es.urjc.dad.devNest.Database;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    Videogame videogame;

    @Column(nullable = false)
    UserEntity user;

    @Column(nullable = true)
    Comment parentComment;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String textComment;

    public Comment() {
    }

    //region SETTERS
    public void setDate(String date) {
        this.date = date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    //endregion

    //region GETTERS


    public UserEntity getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTextComment() {
        return textComment;
    }
    //endregion

}
