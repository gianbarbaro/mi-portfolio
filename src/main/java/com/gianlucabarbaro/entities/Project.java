
package com.gianlucabarbaro.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Projects")
public class Project implements Serializable {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String project_name;
    private String description;
    
    @OneToOne
    @JoinColumn(name = "project_photo")
    private Photo photo;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploaded_date;
    
    private Boolean active;

    public Project() {
    }

    public Project(String id, String project_name, String description, Photo photo, Date uploaded_date, Boolean active) {
        this.id = id;
        this.project_name = project_name;
        this.description = description;
        this.photo = photo;
        this.uploaded_date = uploaded_date;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Date getUploaded_date() {
        return uploaded_date;
    }

    public void setUploaded_date(Date uploaded_date) {
        this.uploaded_date = uploaded_date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", project_name=" + project_name + ", description=" + description + ", photo=" + photo + ", uploaded_date=" + uploaded_date + ", active=" + active + '}';
    }
    
    
}
