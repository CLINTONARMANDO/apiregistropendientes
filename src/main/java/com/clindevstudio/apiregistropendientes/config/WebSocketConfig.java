package com.clindevstudio.apiregistropendientes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Mensajes que los clientes pueden suscribirse
        config.setApplicationDestinationPrefixes("/app"); // Prefijo para enviar mensajes desde el cliente
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Endpoint principal
                .setAllowedOrigins("*") // Permitir orígenes (luego puedes limitar)
                .withSockJS(); // Compatibilidad con navegadores antiguos
    }
}
