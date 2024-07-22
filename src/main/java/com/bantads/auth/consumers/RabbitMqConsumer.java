package com.bantads.auth.consumers;

import com.bantads.auth.config.rabbitmq.RabbitMqConfig;
import com.bantads.auth.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMqConsumer {

    private final UsuarioService usuarioService;

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void consumirMensagem(String message) {
        //usuarioService.criarOuAtualizarUsuario();
    }
}
