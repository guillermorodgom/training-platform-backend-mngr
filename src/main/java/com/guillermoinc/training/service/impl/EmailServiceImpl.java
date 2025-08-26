package com.guillermoinc.training.service.impl;

import com.guillermoinc.training.entity.Curso;
import com.guillermoinc.training.service.EmailService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    
    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;
    
    @Value("${sendgrid.from.email}")
    private String fromEmail;
    
    @Value("${sendgrid.from.name}")
    private String fromName;
    
    @Value("${notification.admin.email}")
    private String adminEmail;
    
    @Override
    public void enviarNotificacionNuevoCurso(Curso curso) {
        String asunto = "Nuevo Curso Disponible: " + curso.getTitulo();
        String contenido = construirContenidoNotificacion(curso);
        
        try {
            enviarEmail(adminEmail, asunto, contenido);
            log.info("Notificacion enviada exitosamente para el curso: {} (ID: {})", 
                    curso.getTitulo(), curso.getIdCurso());
        } catch (Exception e) {
            log.error("Error al enviar notificacion para el curso: {} (ID: {}). Error: {}", 
                    curso.getTitulo(), curso.getIdCurso(), e.getMessage());
        }
    }
    
    @Override
    public void enviarEmail(String destinatario, String asunto, String contenido) {
        Email from = new Email(fromEmail, fromName);
        Email to = new Email(destinatario);
        Content content = new Content("text/html", contenido);
        Mail mail = new Mail(from, asunto, to, content);
        
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            
            Response response = sg.api(request);
            
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                log.info("Email enviado exitosamente a: {}", destinatario);
            } else {
                log.warn("Email enviado con status code: {} para destinatario: {}", 
                        response.getStatusCode(), destinatario);
            }
            
        } catch (IOException e) {
            log.error("Error al enviar email a: {}. Error: {}", destinatario, e.getMessage());
            throw new RuntimeException("Error al enviar email", e);
        }
    }
    
    private String construirContenidoNotificacion(Curso curso) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>")
            .append("<html><head><meta charset=\"UTF-8\">")
            .append("<style>")
            .append("body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }")
            .append(".container { max-width: 600px; margin: 0 auto; }")
            .append(".header { background-color: #007bff; color: white; padding: 20px; text-align: center; }")
            .append(".content { padding: 20px; background-color: #f8f9fa; }")
            .append(".course-info { background-color: white; padding: 15px; border-radius: 5px; margin: 15px 0; }")
            .append(".footer { text-align: center; padding: 20px; font-size: 12px; color: #666; }")
            .append("</style></head><body>")
            .append("<div class=\"container\">")
            .append("<div class=\"header\"><h1>Nuevo Curso Disponible!</h1></div>")
            .append("<div class=\"content\">")
            .append("<h2>Hola Administrador,</h2>")
            .append("<p>Te informamos que se ha creado un nuevo curso en la plataforma de entrenamiento.</p>")
            .append("<div class=\"course-info\">")
            .append("<h3>Detalles del Curso:</h3>")
            .append("<p><strong>ID:</strong> ").append(curso.getIdCurso()).append("</p>")
            .append("<p><strong>Titulo:</strong> ").append(curso.getTitulo()).append("</p>")
            .append("<p><strong>Descripcion:</strong> ").append(curso.getDescripcion() != null ? curso.getDescripcion() : "Sin descripcion").append("</p>")
            .append("<p><strong>Categoria:</strong> ").append(curso.getCategoria() != null ? curso.getCategoria() : "Sin categoria").append("</p>")
            .append("<p><strong>Nivel:</strong> ").append(curso.getNivel() != null ? curso.getNivel() : "Sin nivel").append("</p>")
            .append("<p><strong>Estado:</strong> ").append(curso.getEstado()).append("</p>")
            .append("<p><strong>Fecha de Creacion:</strong> ").append(curso.getFechaCreacion()).append("</p>")
            .append("</div>")
            .append("<p>El curso ya esta disponible para los estudiantes en la plataforma.</p>")
            .append("</div>")
            .append("<div class=\"footer\">")
            .append("<p>2025 Plataforma de Entrenamiento - Notificacion Automatica</p>")
            .append("</div></div></body></html>");
        return html.toString();
    }
}