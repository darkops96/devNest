package es.urjc.dad.devNest.Database.Entities;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class UserEntity implements Serializable{

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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public UserEntity() {
    }

    public UserEntity(String _alias, String _password, String _email, String... roles) {
        super();
        alias = _alias;
        password = _password;
        email = _email;        
        this.roles = List.of(roles);
    }

    public UserEntity(String _alias, String _password, String _email, String _profilePicture, Blob _picture, String... roles) {
        super();
        alias = _alias;
        password = _password;
        email = _email;
        profilePicture = _profilePicture;
        pPictureFile = _picture; 
        this.roles = List.of(roles);
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

    public List<String> getRoles()
    {
        return roles;
    }

    public void setRoles(List<String> roles)
    {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User [username=" + alias + ", email=" + email + "]";
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
        return (int) getId() * getAlias().hashCode();
    }
}
