package com.hotmail.viktorz08.test.spring.testris;

import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TetrisTimer {
    private static final Logger LOG = LoggerFactory.getLogger(TetrisTimer.class);


    private static final long TICK_DELAY = 100;

    private static final Object MONITOR = new Object();


    private static final ConcurrentHashMap<Integer, TetrisBlock> blocks = new ConcurrentHashMap<>();

    private static Timer gameTimer = null;

    public static void addBlock(TetrisBlock block) {
        synchronized (MONITOR) {
            if (blocks.isEmpty()) {
                startTimer();
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
                stopTimer();
            }
        }
    }

    public static void tick() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<TetrisBlock> iterator = TetrisTimer.getBlocks().iterator(); iterator.hasNext(); ) {
            TetrisBlock block = iterator.next();
            block.update(TetrisTimer.getBlocks());
            sb.append(block.getLocationsJson());
            if (iterator.hasNext()) {
                sb.append(',');
            }
        }
        broadcast(String.format("{'type': 'update', 'data' : [%s]}", sb.toString()));
    }

    public static void broadcast(String message) {
        Collection<TetrisBlock> blocks = new CopyOnWriteArrayList<>(TetrisTimer.getBlocks());
        for (TetrisBlock block : blocks) {
            try {
                block.sendMessage(message);
            } catch (Throwable ex) {
                // if TetrisBlock#sendMessage fails the client is removed
                removeBlock(block);
            }
        }
    }

    public static void startTimer() {
        gameTimer = new Timer(TetrisBlock.class.getSimpleName() + " Timer");
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    tick();
                } catch (Throwable ex) {
                    LOG.error("Caught to prevent timer from shutting down", ex);
                }
            }
        }, TICK_DELAY, TICK_DELAY);
    }

    public static void stopTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }

}