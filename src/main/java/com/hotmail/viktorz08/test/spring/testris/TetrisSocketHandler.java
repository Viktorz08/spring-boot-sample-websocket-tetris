package com.hotmail.viktorz08.test.spring.testris;

import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Iterator;

@Component
public class TetrisSocketHandler extends TextWebSocketHandler {
    private static final Logger LOG = LoggerFactory.getLogger(TetrisSocketHandler.class);

    @Autowired
    private TetrisBlock tetrisBlock;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        TetrisTimer.addBlock(this.tetrisBlock);
        this.tetrisBlock.setSession(session);

        String joinMessage = createJoinMessage();
        TetrisTimer.broadcast(String.format("{'type': 'join','data':[%s]}", joinMessage));
    }

    private String createJoinMessage() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<TetrisBlock> iterator = TetrisTimer.getBlocks().iterator(); iterator.hasNext(); ) {
            TetrisBlock block = iterator.next();
            sb.append(String.format("{id: %d, color: '%s'}", block.getId(), block.getHexColor()));
            if (iterator.hasNext()) {
                sb.append(',');
            }
        }

        return sb.toString();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();

        Direction direction = Direction.fromDirection(payload);
        if (direction != null) {
            this.tetrisBlock.setDirection(direction);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        TetrisTimer.removeBlock(this.tetrisBlock);
        TetrisTimer.broadcast(String.format("{'type': 'leave', 'id': %d}", this.tetrisBlock.getId()));
    }

}