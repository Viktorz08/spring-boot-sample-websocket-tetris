package com.hotmail.viktorz08.test.spring.testris.block.impl;

import com.hotmail.viktorz08.test.spring.testris.Direction;
import com.hotmail.viktorz08.test.spring.testris.Location;
import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

public class LineBlock extends TetrisBlock {
    private static final Logger LOG = LoggerFactory.getLogger(LineBlock.class);

    private static final int LINE_BLOCK_SIZE = 4;

    public LineBlock(int id, WebSocketSession session) {
        super(id, session);
    }

    @Override
    protected void fillForm() {
        Collection<Location> tail = this.getBlockParts();
        Location nextLocation = this.getHead();
        tail.add(nextLocation);

        for (int i = 0; i < LINE_BLOCK_SIZE; i++) {
            nextLocation = addBodyPart(tail, nextLocation, Direction.EAST);
        }
    }
}