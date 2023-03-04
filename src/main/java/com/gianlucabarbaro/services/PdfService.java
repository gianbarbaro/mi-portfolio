
package com.gianlucabarbaro.services;

import com.gianlucabarbaro.entities.Pdf;
import com.gianlucabarbaro.entities.Usuario;
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
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Transactional(rollbackFor = {Exception.class})
    public Pdf save(MultipartFile file, String idUser) throws Exception {

        if (file != null) {
            try {
                Pdf pdf = new Pdf();
                pdf.setName(file.getName());
                pdf.setMime(file.getContentType());
                pdf.setContent(file.getBytes());
                
                Usuario user = usuarioService.findById(idUser);
                user.setPdf(pdf);
              
                return pdfRepository.save(pdf);
                
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional(rollbackFor = {Exception.class})
    public Pdf modify(MultipartFile file, String id) {

        if (file != null) {
            try {
                Pdf pdf = new Pdf();
                if (id != null) {
                    Optional<Pdf> respuesta = pdfRepository.findById(id);

                    if (respuesta.isPresent()) {
                        pdf = respuesta.get();

                    }
                }
                pdf.setName(file.getName());
                pdf.setMime(file.getContentType());
                pdf.setContent(file.getBytes());

                return pdfRepository.save(pdf);
                
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional(readOnly = true)
    public Pdf findById(String id) {
        return pdfRepository.getById(id);
    }
    
}
