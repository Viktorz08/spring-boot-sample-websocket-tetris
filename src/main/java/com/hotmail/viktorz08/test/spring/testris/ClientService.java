package com.hotmail.viktorz08.test.spring.testris;

import com.hotmail.viktorz08.test.spring.testris.block.AbstractTetrisBlock;
import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import com.hotmail.viktorz08.test.spring.testris.client.ClientsBroker;
import com.hotmail.viktorz08.test.spring.testris.client.service.ClientNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClientService {
    @Autowired
    TetrisBlock tetrisBlock;

    public static void updateGame() {
        String broadcastUpdate = ClientNotificationService.getBroadcastUpdate();
        broadcast(broadcastUpdate);
    }

    protected static void broadcast(String message) {
        Collection<TetrisBlock> blocks = new CopyOnWriteArrayList<>(ClientsBroker.getBlocks());
        for (TetrisBlock block : blocks) {
            try {
                ((AbstractTetrisBlock) block).sendMessage(message);
            } catch (Throwable ex) {
                // if AbstractTetrisBlock#sendMessage fails the client is removed
                ClientsBroker.removeBlock(block);
            }
        }
    }

    public void joinGame(WebSocketSession session) {
        ClientsBroker.addBlock(this.tetrisBlock);
        this.tetrisBlock.setSession(session);

        String introduceMessage = ClientNotificationService.getBroadcastIntroduceMessage();
        broadcast(introduceMessage);
    }

    public void handleAction(String payload) {
        Direction direction = Direction.fromDirection(payload);
        if (direction != null) {
            this.tetrisBlock.setDirection(direction);
        }
    }

    public void leaveGame() {
        ClientsBroker.removeBlock(this.tetrisBlock);
        String leaveMessage = ClientNotificationService.getLeaveMessage(this.tetrisBlock);
        broadcast(leaveMessage);
    }
}