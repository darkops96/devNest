package es.urjc.dad.devNestInternalService.Database.Entities;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String alias;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String profilePicture;

    @Lob
    @JsonIgnore
    private Blob pPictureFile;

    @Lob
    @Column(length = 512)
    private String description;

    public UserEntity() {
    }

    public UserEntity(String _alias, String _password, String _email) {
        super();
        alias = _alias;
        password = _password;
        email = _email;
    }

    public UserEntity(String _alias, String _password, String _email, String _profilePicture, Blob _picture) {
        super();
        alias = _alias;
        password = _password;
        email = _email;
        profilePicture = _profilePicture;
        pPictureFile = _picture;
    }

    public long getId() {
        return id;
    }

    public void setId(long _id) {
        id = _id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String _alias) {
        alias = _alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        password = _password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String _email) {
        email = _email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String _profilePicture) {
        profilePicture = _profilePicture;
    }

    public Blob getPPictureFile() {
        return pPictureFile;
    }

    public void setPPictureFile(Blob _pPictureFile) {
        pPictureFile = _pPictureFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        description = _description;
    }

    @Override
    public String toString() {
        return "User [username=" + alias + ", email=" + email + "]";
    }
}
