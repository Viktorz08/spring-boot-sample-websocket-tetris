package com.hotmail.viktorz08.test.spring.testris.block.impl;

import com.hotmail.viktorz08.test.spring.testris.location.Direction;
import com.hotmail.viktorz08.test.spring.testris.location.Location;
import com.hotmail.viktorz08.test.spring.testris.block.AbstractTetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class LineBlock extends AbstractTetrisBlock {
    private static final Logger LOG = LoggerFactory.getLogger(LineBlock.class);

    private static final int LINE_BLOCK_SIZE = 4;

    public LineBlock(int id) {
        super(id);
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