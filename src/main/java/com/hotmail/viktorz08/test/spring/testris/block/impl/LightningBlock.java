package com.hotmail.viktorz08.test.spring.testris.block.impl;

import com.hotmail.viktorz08.test.spring.testris.location.Direction;
import com.hotmail.viktorz08.test.spring.testris.location.Location;
import com.hotmail.viktorz08.test.spring.testris.block.AbstractTetrisBlock;
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
        Collection<Location> locations = this.getBlockParts();
        Location nextLocation = this.getHead();
        locations.add(nextLocation);

        nextLocation = addBodyPart(locations, nextLocation, Direction.NORTH);
        nextLocation = addBodyPart(locations, nextLocation, Direction.EAST);
        addBodyPart(locations, nextLocation, Direction.NORTH);
    }
}
