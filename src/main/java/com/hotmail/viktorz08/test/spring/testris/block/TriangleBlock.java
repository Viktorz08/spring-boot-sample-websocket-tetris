package com.hotmail.viktorz08.test.spring.testris.block;

import com.hotmail.viktorz08.test.spring.testris.Direction;
import com.hotmail.viktorz08.test.spring.testris.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

public class TriangleBlock extends TetrisBlock {
    private static final Logger LOG = LoggerFactory.getLogger(TriangleBlock.class);

    public TriangleBlock(int id, WebSocketSession session) {
        super(id, session);
    }


    @Override
    protected void fillForm() {
        Collection<Location> tail = this.getTail();
        Location head = this.getHead();
        tail.add(head);

        Location nextLocation = head.getAdjacentLocation(Direction.EAST);
        tail.add(nextLocation);

        nextLocation = head.getAdjacentLocation(Direction.WEST);
        tail.add(nextLocation);

        nextLocation = head.getAdjacentLocation(Direction.NORTH);
        tail.add(nextLocation);
    }
}