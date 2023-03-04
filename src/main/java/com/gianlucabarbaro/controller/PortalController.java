
package com.gianlucabarbaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalController {
    
    @GetMapping("/")
    public String index(ModelMap model) {
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
    
}
