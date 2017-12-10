package com.hotmail.viktorz08.test.spring.testris;

import com.hotmail.viktorz08.test.spring.testris.block.LineBlock;
import com.hotmail.viktorz08.test.spring.testris.block.SquareBlock;
import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import com.hotmail.viktorz08.test.spring.testris.block.TriangleBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TetrisUtils {
    public static final int PLAYFIELD_WIDTH = 640;
    public static final int PLAYFIELD_HEIGHT = 480;
    public static final int GRID_SIZE = 10;

    private static final Logger LOG = LoggerFactory.getLogger(TetrisUtils.class);

    private static final Random random = new Random();

    private static final List<Class<? extends TetrisBlock>> BLOCK_TYPES = new ArrayList<Class<? extends TetrisBlock>>(){{
        add(LineBlock.class);
        add(TriangleBlock.class);
        add(SquareBlock.class);
    }};


    public static TetrisBlock getRandomForm(int id, WebSocketSession session){
        int nextRand = random.nextInt(BLOCK_TYPES.size());

        Class<? extends TetrisBlock> randBlockClass = BLOCK_TYPES.get(nextRand);

        return instantiateBlock(randBlockClass, id, session);
    }

    private static TetrisBlock instantiateBlock(Class<? extends TetrisBlock> blockClass, int id, WebSocketSession session){
        try {
            Constructor<? extends TetrisBlock> constructor = blockClass.getConstructor(int.class, WebSocketSession.class);
            return constructor.newInstance(id, session);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getRandomHexColor() {
        float hue = random.nextFloat();
        // sat between 0.1 and 0.3
        float saturation = (random.nextInt(2000) + 1000) / 10000f;
        float luminance = 0.9f;
        Color color = Color.getHSBColor(hue, saturation, luminance);
        return '#' + Integer.toHexString((color.getRGB() & 0xffffff) | 0x1000000).substring(1);
    }

    public static Location getRandomLocation() {
        int x = roundByGridSize(random.nextInt(PLAYFIELD_WIDTH));
        int y = roundByGridSize(random.nextInt(PLAYFIELD_HEIGHT));
        return new Location(x, y);
    }

    private static int roundByGridSize(int value) {
        value = value + (GRID_SIZE / 2);
        value = value / GRID_SIZE;
        value = value * GRID_SIZE;
        return value;
    }

}