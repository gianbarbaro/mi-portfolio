
package com.gianlucabarbaro.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Usuario implements Serializable {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String name;
    private String last_name;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    @OneToOne
    @JoinColumn(name = "id_aboutme")
    private About aboutme;
    
    @OneToOne
    @JoinColumn(name = "id_pdf")
    private Pdf pdf;
    
    @OneToMany
    @JoinColumn(name = "user_skills")
    private List<Skill> skills;
    
    @OneToMany
    @JoinColumn(name = "user_projects")
    private List<Project> projects;

    public Usuario() {
        
    }

    public Usuario(String id, String name, String last_name, String email, String password) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        skills = new ArrayList();
        projects = new ArrayList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public About getAboutme() {
        return aboutme;
    }

    public void setAboutme(About aboutme) {
        this.aboutme = aboutme;
    }

    public Pdf getPdf() {
        return pdf;
    }

    public void setPdf(Pdf pdf) {
        this.pdf = pdf;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", name=" + name + ", last_name=" + last_name + ", email=" + email + ", password=" + password + ", aboutme=" + aboutme + ", pdf=" + pdf + ", skills=" + skills + ", projects=" + projects + '}';
    }
    
    

}
