package com.hotmail.viktorz08.test.spring.testris.block;

import com.hotmail.viktorz08.test.spring.testris.location.Direction;
import com.hotmail.viktorz08.test.spring.testris.location.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class LightningBlock extends AbstractTetrisBlock {
    private static final Logger LOG = LoggerFactory.getLogger(LightningBlock.class);

    public LightningBlock(int id) {
        super(id);
    }


    @Override
    protected void fillForm() {
        Collection<Location> tail = this.getBlockParts();
        Location nextLocation = this.getHead();
        tail.add(nextLocation);

        nextLocation = nextLocation.getAdjacentLocation(Direction.EAST);
        tail.add(nextLocation);

        nextLocation = nextLocation.getAdjacentLocation(Direction.NORTH);
        tail.add(nextLocation);

        nextLocation = nextLocation.getAdjacentLocation(Direction.WEST);
        tail.add(nextLocation);
    }
}