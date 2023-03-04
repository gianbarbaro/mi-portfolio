
package com.gianlucabarbaro.controller;

import com.gianlucabarbaro.entities.Usuario;
import com.gianlucabarbaro.services.AboutService;
import com.gianlucabarbaro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/about")
public class AboutController {
    
    @Autowired
    private AboutService aboutService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/edit")
    public String edit(RedirectAttributes attr, ModelMap model, @RequestParam String id, @RequestParam String mainContent, @RequestParam String content) throws Exception {
        Usuario user = usuarioService.findById(id);
        try {
            aboutService.modify(user.getAboutme().getId(), mainContent, content);
            attr.addFlashAttribute("success", "sección 'About me' actualizada");
            return "redirect:/user/admin-settings/" + id;

        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            model.put("error", e.getMessage());
            return "redirect:/user/admin-settings/" + id;
        }
    }
    
}
