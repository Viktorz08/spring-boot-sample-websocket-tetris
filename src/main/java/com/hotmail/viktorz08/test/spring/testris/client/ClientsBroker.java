package com.hotmail.viktorz08.test.spring.testris.client;

import com.hotmail.viktorz08.test.spring.testris.TetrisTimer;
import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class ClientsBroker {
    private static final Logger LOG = LoggerFactory.getLogger(ClientsBroker.class);

    private static final ConcurrentHashMap<Integer, TetrisBlock> blocks = new ConcurrentHashMap<>();

    private static final Object MONITOR = new Object();

    public static void addBlock(TetrisBlock block) {
        synchronized (MONITOR) {
            if (blocks.isEmpty()) {
                TetrisTimer.startTimer();
            }
            blocks.put(block.getId(), block);
        }
    }

    public static Collection<TetrisBlock> getBlocks() {
        return Collections.unmodifiableCollection(blocks.values());
    }

    public static void removeBlock(TetrisBlock block) {
        synchronized (MONITOR) {
            blocks.remove(block.getId());
            if (blocks.isEmpty()) {
                TetrisTimer.stopTimer();
            }
        }
    }
}