
package com.gianlucabarbaro.controller;

import com.gianlucabarbaro.entities.Usuario;
import com.gianlucabarbaro.services.NotificationService;
import com.gianlucabarbaro.services.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private NotificationService notificationService;
    
    @GetMapping("/")
    public String index(ModelMap model) throws Exception {
        List<Usuario> users = usuarioService.findAll();
        model.put("user", users.get(0));
        return "index.html";
    }
    
    @GetMapping("/admin")
    public String login(ModelMap modelo, @RequestParam(required = false) String error, @RequestParam(required = false) String logout) throws Exception {

        if (error != null) {
            modelo.put("error", "Usuario o clave incorrectos");
        }
        if (logout != null) {
            modelo.put("logout", "Sesión cerrada exitosamente");
        }

        return "admin.html";
    }
    
    @GetMapping("/register")
    public String register(ModelMap model) {
        return "register.html";
    }
    
    @PostMapping("/mailsender")
    public String sendMail(@RequestParam String email, @RequestParam String subject, @RequestParam String text) {
        try {
            notificationService.send(email, subject, text);
            return "redirect:/";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }
    
}
