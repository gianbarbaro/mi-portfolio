
package com.gianlucabarbaro.services;

import com.gianlucabarbaro.entities.User;
import com.gianlucabarbaro.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional(rollbackFor = {Exception.class})
    public User create(String name, String last_name, String email, String password) throws Exception {
        
        validar(name, last_name, email, password);
        
        User user = new User();
        
        user.setName(name);
        user.setLast_name(last_name);
        user.setEmail(email);
        user.setPassword(password);
        
        return userRepository.save(user);
    }
    
    @Transactional(rollbackFor = {Exception.class})
    public void modify(String id, String name, String last_name, String email, String password) throws Exception {
        
        validar(name, last_name, email, password);
        
        Optional<User> respuesta = userRepository.findById(id);
        
        if (respuesta.isPresent()) {
            
            User user = respuesta.get();
            
            user.setName(name);
            user.setLast_name(last_name);
            user.setEmail(email);
            user.setPassword(password);
            
            userRepository.save(user);
        } else {
            throw new Exception("El usuario no ha sido encontrado");
        }
    }
    
    @Transactional(readOnly = true)
    public User findById(String id) throws Exception {
        Optional<User> respuesta = userRepository.findById(id);
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
}
