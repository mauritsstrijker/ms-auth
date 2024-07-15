package com.bantads.auth.consumers;

import com.bantads.auth.config.rabbitmq.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConsumer {

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void consumirMensagem(String message) {
        System.out.println("Consumiu mensagem, " + message);
    }
}
