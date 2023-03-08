
package com.gianlucabarbaro.services;

import com.gianlucabarbaro.entities.Photo;
import com.gianlucabarbaro.entities.Project;
import com.gianlucabarbaro.entities.Usuario;
import com.gianlucabarbaro.repositories.ProjectRepository;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private PhotoService photoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Transactional(rollbackFor = {Exception.class})
    public Project save(MultipartFile file, String project_name, String description, URL project_url, String idUser) throws Exception {
        
        if (project_name == null || project_name.isEmpty()) {
            throw new Exception("Debe ingresar el titulo del proyecto");
        }
        if (description == null || description.isEmpty()) {
            throw new Exception("Debe ingresar una descripcion");
        }
        if (project_url == null || project_url.toString().isEmpty()) {
            throw new Exception("Debe ingresar la url de su proyecto");
        }
        
        Project project = new Project();
        
        project.setProject_name(project_name);
        project.setDescription(description);
        project.setUploaded_date(new Date());
        project.setProject_url(project_url);
        project.setActive(true);
        
        Photo photo = photoService.save(file);
        project.setPhoto(photo);
        
        Usuario user = usuarioService.findById(idUser);
        user.getProjects().add(project);
        
        return projectRepository.save(project);
    }
    
    @Transactional(rollbackFor = {Exception.class})
    public void modify(MultipartFile file, String id, String project_name, String description, URL project_url) throws Exception {
        
        if (project_name == null || project_name.isEmpty()) {
            throw new Exception("Debe ingresar el titulo del proyecto");
        }
        if (description == null || description.isEmpty()) {
            throw new Exception("Debe ingresar una descripcion");
        }
        if (project_url == null || project_url.toString().isEmpty()) {
            throw new Exception("Debe ingresar la url de su proyecto");
        }
        
        Optional<Project> respuesta = projectRepository.findById(id);
        
        if (respuesta.isPresent()) {
            
            Project project = respuesta.get();
            
            project.setProject_name(project_name);
            project.setDescription(description);
            project.setProject_url(project_url);
            
            if (file != null) {
                String idPhoto = null;
                if (project.getPhoto()!= null) {

                    idPhoto = project.getPhoto().getId();

                }
                Photo photo = photoService.modify(file, idPhoto);
                project.setPhoto(photo);
            }
            
            projectRepository.save(project);
        } else {
            throw new Exception("Ha ocurrido un error al tratar de modificar el proyecto");
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        Optional<Project> respuesta = projectRepository.findById(id);
        if (respuesta.isPresent()) {
            Project project = respuesta.get();
            projectRepository.delete(project);
        } else {
            throw new Exception("Error al borrar el proyecto");
        }
    }
    
    @Transactional(readOnly = true)
    public Project findById(String id) throws Exception {
        Optional<Project> respuesta = projectRepository.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("No se pudo encontrar el proyecto");
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Project enable(String id) throws Exception {
        Optional<Project> respuesta = projectRepository.findById(id);
        if (respuesta.isPresent()) {
            Project project = respuesta.get();
            project.setActive(true);
            return projectRepository.save(project);
        } else {
            throw new Exception("No se pudo encontrar el proyecto");
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Project disable(String id) throws Exception {
        Optional<Project> respuesta = projectRepository.findById(id);
        if (respuesta.isPresent()) {
            Project project = respuesta.get();
            project.setActive(false);
            return projectRepository.save(project);
        } else {
            throw new Exception("No se pudo encontrar el proyecto");
        }
    }
}
