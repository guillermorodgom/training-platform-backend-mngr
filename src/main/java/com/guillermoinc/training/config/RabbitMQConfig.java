package com.guillermoinc.training.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange cursosExchange() {
        return new TopicExchange("cursos.exchange");
    }

    @Bean
    public Queue cursosCreadoQueue() {
        // Declaras la cola durable llamada "cola.cursos.creados"
        return new Queue("cola.cursos.creados", true);
    }

    @Bean
    public Binding bindingCursosCreado(Queue cursosCreadoQueue, TopicExchange cursosExchange) {
        // Vinculas la cola al exchange con la routing key "curso.creado"
        return BindingBuilder.bind(cursosCreadoQueue)
                .to(cursosExchange)
                .with("curso.creado");
    }
}

 