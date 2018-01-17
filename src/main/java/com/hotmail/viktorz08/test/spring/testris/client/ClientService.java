package com.hotmail.viktorz08.test.spring.testris.client;

import com.hotmail.viktorz08.test.spring.testris.block.AbstractTetrisBlock;
import com.hotmail.viktorz08.test.spring.testris.client.service.ClientNotificationService;
import com.hotmail.viktorz08.test.spring.testris.location.Direction;
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
    Client client;

    public static void updateGame() {
        String broadcastUpdate = ClientNotificationService.getBroadcastUpdate();
        broadcast(broadcastUpdate);
    }

    protected static void broadcast(String message) {
        Collection<Client> clients = new CopyOnWriteArrayList<>(ClientsBroker.getClients());
        for (Client client : clients) {
            try {
                ((AbstractTetrisBlock) client.getTetrisBlock()).sendMessage(message);
            } catch (Throwable ex) {
                // if AbstractTetrisBlock#sendMessage fails the client is removed
                ClientsBroker.removeClient(client);
            }
        }
    }

    public void joinGame(WebSocketSession session) {
        ClientsBroker.addClient(this.client);
        this.client.setSession(session);

        String introduceMessage = ClientNotificationService.getBroadcastIntroduceMessage();
        broadcast(introduceMessage);
    }

    public void handleAction(String payload) {
        Direction direction = Direction.fromDirection(payload);
        if (direction != null) {
            this.client.getTetrisBlock().setDirection(direction);
        }
    }

    public void leaveGame() {
        ClientsBroker.removeClient(this.client);
        String leaveMessage = ClientNotificationService.getLeaveMessage(this.client);
        broadcast(leaveMessage);
    }
}