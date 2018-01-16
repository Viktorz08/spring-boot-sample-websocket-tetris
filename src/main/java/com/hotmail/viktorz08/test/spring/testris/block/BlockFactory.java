package com.hotmail.viktorz08.test.spring.testris.block;

import com.hotmail.viktorz08.test.spring.testris.block.impl.LightningBlock;
import com.hotmail.viktorz08.test.spring.testris.block.impl.LineBlock;
import com.hotmail.viktorz08.test.spring.testris.block.impl.SquareBlock;
import com.hotmail.viktorz08.test.spring.testris.block.impl.TriangleBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class BlockFactory {

    private static final Logger LOG = LoggerFactory.getLogger(BlockFactory.class);

    private static final List<Class<? extends TetrisBlock>> BLOCK_TYPES = new ArrayList<Class<? extends TetrisBlock>>() {{
        add(LineBlock.class);
        add(TriangleBlock.class);
        add(SquareBlock.class);
        add(LightningBlock.class);
    }};

    private static final AtomicInteger blockIds = new AtomicInteger(0);

    private static final Random random = new Random();

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static TetrisBlock createBlock() {
        int id = blockIds.getAndIncrement();
        return createBlock(id);
    }

    protected static TetrisBlock createBlock(int id) {
        Class<? extends TetrisBlock> randBlockClass = getRandomBlockClass();
        return createBlock(id, randBlockClass);
    }

    protected static TetrisBlock createBlock(int id, Class<? extends TetrisBlock> blockClass) {
        TetrisBlock tetrisBlock = instantiateBlock(blockClass, id);
        LOG.debug("with id: {} created block:{}", id, tetrisBlock);

        return tetrisBlock;
    }

    protected static Class<? extends TetrisBlock> getRandomBlockClass() {
        int nextRand = random.nextInt(BLOCK_TYPES.size());
        return BLOCK_TYPES.get(nextRand);
    }

    private static TetrisBlock instantiateBlock(Class<? extends TetrisBlock> blockClass, int id) {
        try {
            Constructor<? extends TetrisBlock> constructor = blockClass.getConstructor(int.class);
            return constructor.newInstance(id);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
