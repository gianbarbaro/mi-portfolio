
package com.gianlucabarbaro.controller;

import com.gianlucabarbaro.services.SkillService;
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
@PreAuthorize("isAuthenticated()")
@RequestMapping("/skill")
public class SkillController {
    
    @Autowired
    private SkillService skillService;
    
    @PostMapping("/add")
    public String add(RedirectAttributes attr, ModelMap model, @RequestParam String id, @RequestParam String skill_name) throws Exception {
        try {
            skillService.save(skill_name, id);
            attr.addFlashAttribute("success", "se añadio una nueva skill");
            return "redirect:/user/admin-settings/" + id;

        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            model.put("error", e.getMessage());
            return "redirect:/user/admin-settings/" + id;
        }
    }
    
    @GetMapping("/enable/{id1}/{id2}")
        public String enable(RedirectAttributes attr, @PathVariable("id1") String id_skill, @PathVariable("id2") String id_user) throws Exception {  
            try {
                skillService.enable(id_skill);
                return "redirect:/user/admin-settings/" + id_user;
            } catch (Exception e) {
                attr.addFlashAttribute("error", e.getMessage());
                return "redirect:/user/admin-settings/" + id_user;
            }
        }
    
    @GetMapping("/disable/{id1}/{id2}")
    public String disable(RedirectAttributes attr, @PathVariable("id1") String id_skill, @PathVariable("id2") String id_user) throws Exception {  
        try {
            skillService.disable(id_skill);
            return "redirect:/user/admin-settings/" + id_user;
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/admin-settings/" + id_user;
        }
    }
    
    @GetMapping("/delete/{id1}/{id2}")
    public String delete(RedirectAttributes attr, @PathVariable("id1") String id_skill, @PathVariable("id2") String id_user) throws Exception {  
        try {
            skillService.delete(id_skill);
            attr.addFlashAttribute("success", "se elimino la skill seleccionada");
            return "redirect:/user/admin-settings/" + id_user;
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/admin-settings/" + id_user;
        }
    }
    
}
