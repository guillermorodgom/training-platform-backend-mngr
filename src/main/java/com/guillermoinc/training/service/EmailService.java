package com.guillermoinc.training.service;

import com.guillermoinc.training.entity.Curso;

public interface EmailService {
    
    void enviarNotificacionNuevoCurso(Curso curso);
    
    void enviarEmail(String destinatario, String asunto, String contenido);
}