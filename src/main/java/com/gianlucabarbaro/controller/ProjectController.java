
package com.gianlucabarbaro.controller;

import com.gianlucabarbaro.services.ProjectService;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("isAuthenticated()")
@RequestMapping("/project")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @PostMapping("/add")
    public String add(RedirectAttributes attr, ModelMap model, MultipartFile file, @RequestParam String project_name, @RequestParam String description, @RequestParam URL project_url,@RequestParam String id) throws Exception {
        try {
            projectService.save(file, project_name, description, project_url, id);
            attr.addFlashAttribute("success", "se añadio un nuevo proyecto");
            return "redirect:/user/admin-settings/" + id;

        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            model.put("error", e.getMessage());
            return "redirect:/user/admin-settings/" + id;
        }
    }
    
    @GetMapping("/enable/{id1}/{id2}")
        public String enable(RedirectAttributes attr, @PathVariable("id1") String id_project, @PathVariable("id2") String id_user) throws Exception {  
            try {
                projectService.enable(id_project);
                return "redirect:/user/admin-settings/" + id_user;
            } catch (Exception e) {
                attr.addFlashAttribute("error", e.getMessage());
                return "redirect:/user/admin-settings/" + id_user;
            }
        }
    
    @GetMapping("/disable/{id1}/{id2}")
    public String disable(RedirectAttributes attr, @PathVariable("id1") String id_project, @PathVariable("id2") String id_user) throws Exception {  
        try {
            projectService.disable(id_project);
            return "redirect:/user/admin-settings/" + id_user;
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/admin-settings/" + id_user;
        }
    }
    
    @GetMapping("/delete/{id1}/{id2}")
    public String delete(RedirectAttributes attr, @PathVariable("id1") String id_project, @PathVariable("id2") String id_user) throws Exception {  
        try {
            projectService.delete(id_project);
            return "redirect:/user/admin-settings/" + id_user;
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/admin-settings/" + id_user;
        }
    }
    
}
