
package com.gianlucabarbaro.controller;

import com.gianlucabarbaro.entities.Pdf;
import com.gianlucabarbaro.entities.Usuario;
import com.gianlucabarbaro.services.PdfService;
import com.gianlucabarbaro.services.UsuarioService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pdf")
public class PdfController {
    
    @Autowired
    private PdfService pdfService;
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> pdf(@PathVariable String id) {

        try {
            
            Pdf pdf = pdfService.findById(id);
            
            byte[] content = pdf.getContent();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(content, headers, HttpStatus.OK);

        } catch (Exception ex) {
            Logger.getLogger(PdfController.class.getName()).log(Level.SEVERE, null, ex);

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/edit")
    public String edit(RedirectAttributes attr, ModelMap model, @RequestParam String id, MultipartFile file) throws Exception {
        Usuario user = usuarioService.findById(id);
        try {
            pdfService.modify(file, user.getPdf().getId());
            attr.addFlashAttribute("success", "CV actulizado");
            return "redirect:/user/admin-settings/" + id;

        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            model.put("error", e.getMessage());
            return "redirect:/user/admin-settings/" + id;
        }
    }
    
}
