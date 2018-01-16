package com.hotmail.viktorz08.test.spring.testris.block.impl;

import com.hotmail.viktorz08.test.spring.testris.Direction;
import com.hotmail.viktorz08.test.spring.testris.Location;
import com.hotmail.viktorz08.test.spring.testris.block.AbstractTetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class TriangleBlock extends AbstractTetrisBlock {
    private static final Logger LOG = LoggerFactory.getLogger(TriangleBlock.class);

    public TriangleBlock(int id) {
        super(id);
    }


    @Override
    protected void fillForm() {
        Collection<Location> tail = this.getBlockParts();
        Location head = this.getHead();
        tail.add(head);

        addHeadPart(tail, head, Direction.EAST);
        addHeadPart(tail, head, Direction.WEST);
        addHeadPart(tail, head, Direction.NORTH);
    }

}