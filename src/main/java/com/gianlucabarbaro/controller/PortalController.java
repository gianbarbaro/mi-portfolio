
package com.gianlucabarbaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalController {
    
    @GetMapping("/")
    public String index(ModelMap model) {
        return "index.html";
    }
    
    @GetMapping("/admin")
    public String admin(ModelMap model) {
        return "admin.html";
    }
}
