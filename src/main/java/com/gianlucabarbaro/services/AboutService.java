
package com.gianlucabarbaro.services;

import com.gianlucabarbaro.entities.About;
import com.gianlucabarbaro.entities.Usuario;
import com.gianlucabarbaro.repositories.AboutRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AboutService {
    
    @Autowired
    private AboutRepository aboutRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Transactional(rollbackFor = {Exception.class})
    public About save(String mainContent, String content, String idUser) throws Exception {
        
        if (mainContent == null || mainContent.isEmpty()) {
            throw new Exception("Debe ingresar el texto correspondiente");
        }
        if (content == null || content.isEmpty()) {
            throw new Exception("Debe ingresar el texto correspondiente");
        }
        
        About about = new About();
        
        about.setMainContent(mainContent);
        about.setContent(content);
        
        Usuario user = usuarioService.findById(idUser);
        user.setAboutme(about);
        
        return aboutRepository.save(about);
    }
    
    @Transactional(rollbackFor = {Exception.class})
    public void modify(String id, String mainContent, String content) throws Exception {
        
        if (mainContent == null || mainContent.isEmpty()) {
            throw new Exception("Debe ingresar el texto correspondiente");
        }
        if (content == null || content.isEmpty()) {
            throw new Exception("Debe ingresar el texto correspondiente");
        }
        
        Optional<About> respuesta = aboutRepository.findById(id);
        
        if (respuesta.isPresent()) {
            
            About about = respuesta.get();
        
            about.setMainContent(mainContent);
            about.setContent(content);
            
            aboutRepository.save(about);
        } else {
            throw new Exception("Ha ocurrido un error al tratar de modificar");
        }
    }
    
    @Transactional(readOnly = true)
    public About findById(String id) throws Exception {
        Optional<About> respuesta = aboutRepository.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("No se pudo encontrar la información solicitada");
        }
    }
}
