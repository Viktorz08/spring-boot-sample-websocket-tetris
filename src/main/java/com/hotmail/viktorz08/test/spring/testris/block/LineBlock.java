package com.hotmail.viktorz08.test.spring.testris.block;

import com.hotmail.viktorz08.test.spring.testris.Direction;
import com.hotmail.viktorz08.test.spring.testris.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

public class LineBlock extends TetrisBlock{
    private static final Logger LOG = LoggerFactory.getLogger(LineBlock.class);


    public LineBlock(int id, WebSocketSession session) {
        super(id, session);
    }

    @Override
    protected void fillForm() {
        Collection<Location> tail = this.getTail();
        Location nextLocation = this.getHead();
        tail.add(nextLocation);

        nextLocation = nextLocation.getAdjacentLocation(Direction.EAST);
        tail.add(nextLocation);

        nextLocation = nextLocation.getAdjacentLocation(Direction.EAST);
        tail.add(nextLocation);

        nextLocation = nextLocation.getAdjacentLocation(Direction.EAST);
        tail.add(nextLocation);

        nextLocation = nextLocation.getAdjacentLocation(Direction.EAST);
        tail.add(nextLocation);
    }
}