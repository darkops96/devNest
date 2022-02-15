package es.urjc.dad.devNest.Database;

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

    public UserEntity() {}

    public long getId()
    {
        return id;
    }

    public void setId(long _id)
    {
        id = _id;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String _alias)
    {
        alias = _alias;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String _password)
    {
        password = _password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String _email)
    {
        email = _email;
    }

    public String getProfilePicture()
    {
        return profilePicture;
    }

    public void setProfilePicture(String _profilePicture)
    {
        profilePicture = _profilePicture;
    }

    public String getPPictureFile()
    {
        return profilePicture;
    }

    public void setPPictureFile(Blob _pPictureFile)
    {
        pPictureFile = _pPictureFile;
    }
}
