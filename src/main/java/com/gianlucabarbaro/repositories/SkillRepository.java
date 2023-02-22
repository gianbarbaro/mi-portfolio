
package com.gianlucabarbaro.repositories;

import com.gianlucabarbaro.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, String> {
    
}
