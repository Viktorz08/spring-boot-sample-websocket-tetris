package com.hotmail.viktorz08.test.spring.testris;

import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import com.hotmail.viktorz08.test.spring.testris.client.service.ClientNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClientService {
    @Autowired
    TetrisBlock tetrisBlock;

    public void joinGame(WebSocketSession session) {
        TetrisTimer.addBlock(this.tetrisBlock);
        this.tetrisBlock.setSession(session);

        String introduceMessage = ClientNotificationService.getBroadcastIntroduceMessage();
        TetrisTimer.broadcast(introduceMessage);
    }

    public void handleAction(String payload) {
        Direction direction = Direction.fromDirection(payload);
        if (direction != null) {
            this.tetrisBlock.setDirection(direction);
        }
    }

    public void leaveGame() {
        TetrisTimer.removeBlock(this.tetrisBlock);
        String leaveMessage = ClientNotificationService.getLeaveMessage(this.tetrisBlock);
        TetrisTimer.broadcast(leaveMessage);
    }

}