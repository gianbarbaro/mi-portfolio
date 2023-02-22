
package com.gianlucabarbaro.services;

import com.gianlucabarbaro.entities.Pdf;
import com.gianlucabarbaro.repositories.PdfRepository;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfService {
    
    @Autowired
    private PdfRepository pdfRepository;
    
    @Transactional(rollbackFor = {Exception.class})
    public Pdf save(MultipartFile archive) {

        if (archive != null) {
            try {
                Pdf photo = new Pdf();
                photo.setName(archive.getName());
                photo.setMime(archive.getContentType());
                photo.setContent(archive.getBytes());
              
                return pdfRepository.save(photo);
                
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional(rollbackFor = {Exception.class})
    public Pdf modify(MultipartFile archivo, String id) {

        if (archivo != null) {
            try {
                Pdf photo = new Pdf();
                if (id != null) {
                    Optional<Pdf> respuesta = pdfRepository.findById(id);

                    if (respuesta.isPresent()) {
                        photo = respuesta.get();

                    }
                }
                photo.setName(archivo.getName());
                photo.setMime(archivo.getContentType());
                photo.setContent(archivo.getBytes());

                return pdfRepository.save(photo);
                
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional(readOnly = true)
    public Pdf fundById(String id) {
        return pdfRepository.getById(id);
    }
    
}
