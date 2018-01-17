package com.hotmail.viktorz08.test.spring.testris;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class TetrisSocketHandler extends TextWebSocketHandler {
    private static final Logger LOG = LoggerFactory.getLogger(TetrisSocketHandler.class);

    @Autowired
    private ClientService clientService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        clientService.joinGame(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        clientService.handleAction(payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        clientService.leaveGame();
    }

}