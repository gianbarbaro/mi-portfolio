
package com.gianlucabarbaro.services;

import com.gianlucabarbaro.entities.Photo;
import com.gianlucabarbaro.entities.Project;
import com.gianlucabarbaro.repositories.ProjectRepository;
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
    
    @Transactional(rollbackFor = {Exception.class})
    public Project save(MultipartFile archivo, String project_name, String description) throws Exception {
        
        if (project_name == null || project_name.isEmpty()) {
            throw new Exception("Debe ingresar el titulo del proyecto");
        }
        if (description == null || description.isEmpty()) {
            throw new Exception("Debe ingresar una descripcion");
        }
        
        Project project = new Project();
        
        project.setProject_name(project_name);
        project.setDescription(description);
        project.setUploaded_date(new Date());
        project.setActive(true);
        
        Photo photo = photoService.save(archivo);
        project.setPhoto(photo);
        
        return projectRepository.save(project);
    }
    
    @Transactional(rollbackFor = {Exception.class})
    public void modify(MultipartFile archivo, String id, String project_name, String description) throws Exception {
        
        if (project_name == null || project_name.isEmpty()) {
            throw new Exception("Debe ingresar el titulo del proyecto");
        }
        if (description == null || description.isEmpty()) {
            throw new Exception("Debe ingresar una descripcion");
        }
        
        Optional<Project> respuesta = projectRepository.findById(id);
        
        if (respuesta.isPresent()) {
            
            Project project = respuesta.get();
            
            project.setProject_name(project_name);
            project.setDescription(description);
            
            if (archivo != null) {
                String idPhoto = null;
                if (project.getPhoto()!= null) {

                    idPhoto = project.getPhoto().getId();

                }
                Photo photo = photoService.modify(archivo, idPhoto);
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
