package com.hotmail.viktorz08.test.spring.testris.block.impl;

import com.hotmail.viktorz08.test.spring.testris.Direction;
import com.hotmail.viktorz08.test.spring.testris.Location;
import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

public class SquareBlock extends TetrisBlock {
    private static final Logger LOG = LoggerFactory.getLogger(SquareBlock.class);

    public SquareBlock(int id, WebSocketSession session) {
        super(id, session);
    }


    @Override
    protected void fillForm() {
        Collection<Location> tail = this.getBlockParts();
        Location nextLocation = this.getHead();
        tail.add(nextLocation);

        nextLocation = addBodyPart(tail, nextLocation, Direction.EAST);
        nextLocation = addBodyPart(tail, nextLocation, Direction.NORTH);
        addBodyPart(tail, nextLocation, Direction.WEST);
    }

}