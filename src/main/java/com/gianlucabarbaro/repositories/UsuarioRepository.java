
package com.gianlucabarbaro.repositories;

import com.gianlucabarbaro.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    
    @Query("SELECT u FROM Usuario u WHERE u.email LIKE :email")
    public Usuario findByEmail(@Param("email") String email);
    
}
