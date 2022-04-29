package es.urjc.dad.devNest.Database.Entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class CommentEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private VideogameEntity videogame;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    private CommentEntity parentComment;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String textComment;

    //Constructor
    public CommentEntity() {
    }

    //comentario padre
    public CommentEntity(VideogameEntity _videogame, UserEntity _user, String _date, String _comment) {
        super();
        videogame = _videogame;
        user = _user;
        date = _date;
        textComment = _comment;
    }

    //comentario hijo
    public CommentEntity(VideogameEntity _videogame, UserEntity _user, CommentEntity _commentParent, String _date, String _comment) {
        super();
        videogame = _videogame;
        user = _user;
        parentComment = _commentParent;
        date = _date;
        textComment = _comment;
    }


    //region SETTERS

    public void setId(long id) {
        this.id = id;
    }

    public void setVideogame(VideogameEntity videogame) {
        this.videogame = videogame;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setParentComment(CommentEntity parentComment) {
        this.parentComment = parentComment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    //endregion

    //region GETTERS

    public long getId() {
        return id;
    }

    public VideogameEntity getVideogame() {
        return videogame;
    }

    public UserEntity getUser() {
        return user;
    }

    public CommentEntity getParentComment() {
        return parentComment;
    }

    public String getDate() {
        return date;
    }

    public String getTextComment() {
        return textComment;
    }
    //endregion

    @Override
    public String toString() {
        return "Comment [comment=" + textComment + ", user=" + user.getAlias() + "]";
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
        return (int) getId() * getDate().hashCode();
    }
}
