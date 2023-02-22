
package com.gianlucabarbaro.repositories;

import com.gianlucabarbaro.entities.Pdf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepository extends JpaRepository<Pdf, String> {
    
}
