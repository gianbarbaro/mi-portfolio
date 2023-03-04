
package com.gianlucabarbaro.services;

import com.gianlucabarbaro.entities.Photo;
import com.gianlucabarbaro.repositories.PhotoRepository;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {
    
    @Autowired 
    private PhotoRepository photoRepository;
    
    @Transactional(rollbackFor = {Exception.class})
    public Photo save(MultipartFile file) {

        if (file != null) {
            try {
                Photo photo = new Photo();
                photo.setName(file.getName());
                photo.setMime(file.getContentType());
                photo.setContent(file.getBytes());
              
                return photoRepository.save(photo);
                
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional(rollbackFor = {Exception.class})
    public Photo modify(MultipartFile file, String id) {

        if (file != null) {
            try {
                Photo photo = new Photo();
                if (id != null) {
                    Optional<Photo> respuesta = photoRepository.findById(id);

                    if (respuesta.isPresent()) {
                        photo = respuesta.get();

                    }
                }
                photo.setName(file.getName());
                photo.setMime(file.getContentType());
                photo.setContent(file.getBytes());

                return photoRepository.save(photo);
                
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional(readOnly = true)
    public Photo findById(String id) {
        return photoRepository.getById(id);
    }
}
