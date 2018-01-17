package com.hotmail.viktorz08.test.spring.testris.block.impl;

import com.hotmail.viktorz08.test.spring.testris.location.Direction;
import com.hotmail.viktorz08.test.spring.testris.location.Location;
import com.hotmail.viktorz08.test.spring.testris.block.AbstractTetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class SquareBlock extends AbstractTetrisBlock {
    private static final Logger LOG = LoggerFactory.getLogger(SquareBlock.class);

    public SquareBlock(int id) {
        super(id);
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