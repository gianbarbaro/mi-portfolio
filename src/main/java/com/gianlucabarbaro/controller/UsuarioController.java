
package com.gianlucabarbaro.controller;

import com.gianlucabarbaro.entities.Usuario;
import com.gianlucabarbaro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/register")
    public String save(RedirectAttributes attr, ModelMap model, @RequestParam String name, @RequestParam String last_name, @RequestParam String email, @RequestParam String password) {

        try {
            usuarioService.create(name, last_name, email, password);
            attr.addFlashAttribute("success", "se ha registrado exitosamente");
            return "redirect:/admin";
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            model.put("error", e.getMessage());
            return "redirect:/register";
        }
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/admin-settings/{id}")
    public String adminSettings(ModelMap model, @PathVariable String id) throws Exception {
        Usuario user = usuarioService.findById(id);
        model.put("user", user);
        return "admin-settings.html";
    }
    
}
