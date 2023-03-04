
package com.gianlucabarbaro.services;

import com.gianlucabarbaro.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.gianlucabarbaro.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Transactional(rollbackFor = {Exception.class})
    public Usuario create(String name, String last_name, String email, String password) throws Exception {
        
        validar(name, last_name, email, password);
        
        Usuario user = new Usuario();
        
        user.setName(name);
        user.setLast_name(last_name);
        user.setEmail(email);
        
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encryptedPassword);
        
        return usuarioRepository.save(user);
    }
    
    @Transactional(rollbackFor = {Exception.class})
    public void modify(String id, String name, String last_name, String email, String password) throws Exception {
        
        validar(name, last_name, email, password);
        
        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        
        if (respuesta.isPresent()) {
            
            Usuario user = respuesta.get();
            
            user.setName(name);
            user.setLast_name(last_name);
            user.setEmail(email);
            user.setPassword(password);
            
            usuarioRepository.save(user);
        } else {
            throw new Exception("El usuario no ha sido encontrado");
        }
    }
    
    @Transactional(readOnly = true)
    public Usuario findById(String id) throws Exception {
        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("El usuario no ha sido encontrado");
        }
    }
    
    private void validar(String name, String last_name, String email, String password) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("Debe ingresar su nombre");
        }
        if (last_name == null || last_name.trim().isEmpty()) {
            throw new Exception("Debe ingresar su apellido");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new Exception("Debe ingresar su correo electrónico");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new Exception("Debe ingresar una contraseña");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario user = usuarioRepository.findByEmail(email);

        if (user == null) {
            return null;
        }
        
        List<GrantedAuthority> permissions = new ArrayList<>();

        GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_ADMIN");
        permissions.add(p1);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("usersession", user);

        return new User(user.getEmail(), user.getPassword(), permissions);
    }
}
