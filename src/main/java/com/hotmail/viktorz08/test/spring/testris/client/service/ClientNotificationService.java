package com.hotmail.viktorz08.test.spring.testris.client.service;

import com.hotmail.viktorz08.test.spring.testris.TetrisTimer;
import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class ClientNotificationService {
    private static final Logger LOG = LoggerFactory.getLogger(ClientNotificationService.class);


    public static String getBroadcastIntroduceMessage() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<TetrisBlock> iterator = TetrisTimer.getBlocks().iterator(); iterator.hasNext(); ) {
            TetrisBlock block = iterator.next();
            sb.append(String.format("{id: %d, color: '%s'}", block.getId(), block.getHexColor()));
            if (iterator.hasNext()) {
                sb.append(',');
            }
        }

        return String.format("{'type': 'join','data':[%s]}", sb.toString());
    }

    public static String getLeaveMessage(TetrisBlock tetrisBlock) {
        return String.format("{'type': 'leave', 'id': %d}", tetrisBlock.getId());
    }
}