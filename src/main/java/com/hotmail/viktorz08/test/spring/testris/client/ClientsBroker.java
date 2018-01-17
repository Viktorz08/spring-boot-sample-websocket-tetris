package com.hotmail.viktorz08.test.spring.testris.client;

import com.hotmail.viktorz08.test.spring.testris.TetrisTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class ClientsBroker {
    private static final Logger LOG = LoggerFactory.getLogger(ClientsBroker.class);

    private static final ConcurrentHashMap<Integer, Client> clients = new ConcurrentHashMap<>();

    private static final Object MONITOR = new Object();

    public static void addClient(Client client) {
        synchronized (MONITOR) {
            if (clients.isEmpty()) {
                TetrisTimer.startTimer();
            }
            clients.put(client.getId(), client);
        }
    }

    public static Collection<Client> getClients() {
        return Collections.unmodifiableCollection(clients.values());
    }

    public static void removeClient(Client client) {
        synchronized (MONITOR) {
            clients.remove(client.getId());
            if (clients.isEmpty()) {
                TetrisTimer.stopTimer();
            }
        }
    }
}