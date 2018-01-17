package com.hotmail.viktorz08.test.spring.testris.client.service;

import com.hotmail.viktorz08.test.spring.testris.block.AbstractTetrisBlock;
import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import com.hotmail.viktorz08.test.spring.testris.client.Client;
import com.hotmail.viktorz08.test.spring.testris.client.ClientsBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ClientNotificationService {
    private static final Logger LOG = LoggerFactory.getLogger(ClientNotificationService.class);


    public static String getBroadcastIntroduceMessage() {
        String sb = ClientsBroker.getClients().stream()
                .map(client -> String.format("{id: %d, color: '%s'}", client.getId(), client.getTetrisBlock().getHexColor()))
                .collect(Collectors.joining(","));

        return String.format("{'type': 'join','data':[%s]}", sb);
    }

    public static String getLeaveMessage(Client client) {
        return String.format("{'type': 'leave', 'id': %d}", client.getId());
    }

    public static String getBroadcastUpdate() {
        List<TetrisBlock> tetrisBlocks = ClientsBroker.getClients().stream()
                .map(Client::getTetrisBlock)
                .collect(Collectors.toList());

        String blockLocations = ClientsBroker.getClients().stream()
                .map(client -> {
                    AbstractTetrisBlock tetrisBlock = (AbstractTetrisBlock) client.getTetrisBlock();
                    tetrisBlock.update(tetrisBlocks);
                    return tetrisBlock.getLocationsJson();
                })
                .collect(Collectors.joining(","));
        return String.format("{'type': 'update', 'data' : [%s]}", blockLocations);
    }
}