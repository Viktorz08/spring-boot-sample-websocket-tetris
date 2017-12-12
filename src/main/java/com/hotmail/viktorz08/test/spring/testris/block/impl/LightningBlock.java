package com.hotmail.viktorz08.test.spring.testris.block.impl;

import com.hotmail.viktorz08.test.spring.testris.Direction;
import com.hotmail.viktorz08.test.spring.testris.Location;
import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

public class LightningBlock extends TetrisBlock {

    private static final Logger LOG = LoggerFactory.getLogger(LightningBlock.class);

    public LightningBlock(int id, WebSocketSession session) {
        super(id, session);
    }

    @Override
    protected void fillForm() {
        Collection<Location> locations = this.getBlockParts();
        Location nextLocation = this.getHead();
        locations.add(nextLocation);

        nextLocation = addBodyPart(locations, nextLocation, Direction.NORTH);
        nextLocation = addBodyPart(locations, nextLocation, Direction.EAST);
        addBodyPart(locations, nextLocation, Direction.NORTH);
    }
}
