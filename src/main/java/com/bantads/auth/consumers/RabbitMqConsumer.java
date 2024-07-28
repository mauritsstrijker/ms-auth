package com.bantads.auth.consumers;

import com.bantads.auth.config.rabbitmq.RabbitMqConfig;
import com.bantads.auth.entities.Usuario;
import com.bantads.auth.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMqConsumer {

    private final UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void consumirMensagem(String message) {
        try {
            Usuario usuario = objectMapper.readValue(message, Usuario.class);
            ResponseEntity<String> response = usuarioService.criarOuAtualizarUsuario(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
