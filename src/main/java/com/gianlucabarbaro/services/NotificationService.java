
package com.gianlucabarbaro.services;

import com.gianlucabarbaro.entities.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${spring.mail.password}")
    private String mailPassword;
    
    @Async
    public void send(String email, String subject, String text) throws Exception {
        
        List<Usuario> users = usuarioService.findAll();
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(users.get(0).getEmail());
        message.setFrom(email);
        message.setSubject(subject);
        message.setText(text);
        
        System.out.println("spring mail " + mailFrom);
        System.out.println("spring password " + mailPassword);
        
        mailSender.send(message);
    }
}
