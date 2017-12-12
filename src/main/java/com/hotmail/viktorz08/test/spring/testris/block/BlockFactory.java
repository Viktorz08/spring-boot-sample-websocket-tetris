package com.hotmail.viktorz08.test.spring.testris.block;

import com.hotmail.viktorz08.test.spring.testris.block.impl.LightningBlock;
import com.hotmail.viktorz08.test.spring.testris.block.impl.LineBlock;
import com.hotmail.viktorz08.test.spring.testris.block.impl.SquareBlock;
import com.hotmail.viktorz08.test.spring.testris.block.impl.TriangleBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockFactory {

    private static final Logger LOG = LoggerFactory.getLogger(BlockFactory.class);

    private static final List<Class<? extends TetrisBlock>> BLOCK_TYPES = new ArrayList<Class<? extends TetrisBlock>>() {{
        add(LineBlock.class);
        add(TriangleBlock.class);
        add(SquareBlock.class);
        add(LightningBlock.class);
    }};

    private static final Random random = new Random();

    public static TetrisBlock createBlock(int id, WebSocketSession session) {
        Class<? extends TetrisBlock> randBlockClass = getRandomBlockClass();
        return createBlock(id, session, randBlockClass);
    }

    public static TetrisBlock createBlock(int id, WebSocketSession session, Class<? extends TetrisBlock> blockClass) {
        TetrisBlock tetrisBlock = instantiateBlock(blockClass, id, session);
        LOG.debug("with id: {} for session:{} created block:{}", id, session, tetrisBlock);

        return tetrisBlock;
    }

    protected static Class<? extends TetrisBlock> getRandomBlockClass() {
        int nextRand = random.nextInt(BLOCK_TYPES.size());
        return BLOCK_TYPES.get(nextRand);
    }

    private static TetrisBlock instantiateBlock(Class<? extends TetrisBlock> blockClass, int id, WebSocketSession session) {
        try {
            Constructor<? extends TetrisBlock> constructor = blockClass.getConstructor(int.class, WebSocketSession.class);
            return constructor.newInstance(id, session);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
