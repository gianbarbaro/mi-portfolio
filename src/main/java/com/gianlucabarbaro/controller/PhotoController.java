
package com.gianlucabarbaro.controller;

import com.gianlucabarbaro.entities.Photo;
import com.gianlucabarbaro.services.PhotoService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/photo")
public class PhotoController {
    
    @Autowired
    private PhotoService photoService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> photo(@PathVariable String id) {

        try {
            
            Photo photo = photoService.findById(id);
            
            byte[] content = photo.getContent();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(content, headers, HttpStatus.OK);

        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    
}
