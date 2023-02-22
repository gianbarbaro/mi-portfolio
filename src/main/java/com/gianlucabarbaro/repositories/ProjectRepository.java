
package com.gianlucabarbaro.repositories;

import com.gianlucabarbaro.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    
}
