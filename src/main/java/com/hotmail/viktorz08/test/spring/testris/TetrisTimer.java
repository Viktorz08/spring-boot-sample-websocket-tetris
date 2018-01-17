package com.hotmail.viktorz08.test.spring.testris;

import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class TetrisTimer {
    private static final Logger LOG = LoggerFactory.getLogger(TetrisTimer.class);

    private static final long TICK_DELAY = 100;

    private static Timer gameTimer = null;

    public static void startTimer() {
        gameTimer = new Timer(TetrisBlock.class.getSimpleName() + " Timer");
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    ClientService.updateGame();
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