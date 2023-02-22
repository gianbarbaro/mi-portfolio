
package com.gianlucabarbaro.services;

import com.gianlucabarbaro.entities.Skill;
import com.gianlucabarbaro.repositories.SkillRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkillService {
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Transactional(rollbackFor = {Exception.class})
    public Skill save(String skill_name) throws Exception {
        
        if (skill_name == null || skill_name.isEmpty()) {
            throw new Exception("Debe ingresar el nombre de la skill que desea agregar");
        }
        
        Skill skill = new Skill();
        
        skill.setSkill_name(skill_name);
        skill.setActive(true);
        
        return skillRepository.save(skill);
    }
    
    @Transactional(rollbackFor = {Exception.class})
    public void modify(String id, String skill_name) throws Exception {
        
        if (skill_name == null || skill_name.isEmpty()) {
            throw new Exception("Debe ingresar el nuevo nombre");
        }
        
        Optional<Skill> respuesta = skillRepository.findById(id);
        
        if (respuesta.isPresent()) {
            
            Skill skill = respuesta.get();
            
            skill.setSkill_name(skill_name);
            
            skillRepository.save(skill);
        } else {
            throw new Exception("Ha ocurrido un error al tratar de modificar");
        }
    }
    
    @Transactional(readOnly = true)
    public Skill findById(String id) throws Exception {
        Optional<Skill> respuesta = skillRepository.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("No se pudo encontrar la skill");
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Skill enable(String id) throws Exception {
        Optional<Skill> respuesta = skillRepository.findById(id);
        if (respuesta.isPresent()) {
            Skill skill = respuesta.get();
            skill.setActive(true);
            return skillRepository.save(skill);
        } else {
            throw new Exception("No se pudo encontrar la skill");
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Skill disable(String id) throws Exception {
        Optional<Skill> respuesta = skillRepository.findById(id);
        if (respuesta.isPresent()) {
            Skill skill = respuesta.get();
            skill.setActive(false);
            return skillRepository.save(skill);
        } else {
            throw new Exception("No se pudo encontrar la skill");
        }
    }
}
