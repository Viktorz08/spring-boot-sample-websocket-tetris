package com.hotmail.viktorz08.test.spring;

import com.hotmail.viktorz08.test.spring.testris.TetrisSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

@Configuration
@EnableWebSocket
public class ServletInitializer extends SpringBootServletInitializer implements WebSocketConfigurer{
    private static final Logger LOG = LoggerFactory.getLogger(ServletInitializer.class);


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(tetrisWebSocketHandler(), "/tetris").withSockJS();
    }


    @Bean
    protected WebSocketHandler tetrisWebSocketHandler(){
        return new PerConnectionWebSocketHandler(TetrisSocketHandler.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ServletInitializer.class);
    }
}