package com.hotmail.viktorz08.test.spring.testris.block;

import com.hotmail.viktorz08.test.spring.testris.Direction;
import com.hotmail.viktorz08.test.spring.testris.Location;
import com.hotmail.viktorz08.test.spring.testris.TetrisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public abstract class TetrisBlock {
    private static final Logger LOG = LoggerFactory.getLogger(TetrisBlock.class);

    private static final int DEFAULT_LENGTH = 5;

    private final Object monitor = new Object();

    private final int id;

    private final Deque<Location> tail = new ArrayDeque<>();
    private final WebSocketSession session;
    private final String hexColor;

    private Direction direction;
    private Location head;

    private int length = DEFAULT_LENGTH;


    public TetrisBlock(int id, WebSocketSession session) {
        this.id = id;
        this.session = session;
        this.hexColor = TetrisUtils.getRandomHexColor();
        resetState();
    }

    private void resetState() {
        this.direction = Direction.NONE;
        this.head = TetrisUtils.getRandomLocation();
        this.tail.clear();

        fillForm();

        this.length = DEFAULT_LENGTH;
    }

    protected abstract void fillForm();

    private void kill() throws Exception {
        synchronized (this.monitor) {
            resetState();
            sendMessage("{'type': 'dead'}");
        }
    }

    private void reward() throws Exception {
        synchronized (this.monitor) {
            this.length++;
            sendMessage("{'type': 'kill'}");
        }
    }

    public void sendMessage(String msg) throws Exception {
        this.session.sendMessage(new TextMessage(msg));
    }

    public void update(Collection<TetrisBlock> blocks) {
        synchronized (this.monitor) {
            tail.forEach(location -> {
                location.moveLocation(this.direction);

                if (location.x >= TetrisUtils.PLAYFIELD_WIDTH) {
                    location.x = 0;
                }
                if (location.y >= TetrisUtils.PLAYFIELD_HEIGHT) {
                    location.y = 0;
                }
                if (location.x < 0) {
                    location.x = TetrisUtils.PLAYFIELD_WIDTH;
                }
                if (location.y < 0) {
                    location.y = TetrisUtils.PLAYFIELD_HEIGHT;
                }
            });


//            Location nextLocation = this.head.getAdjacentLocation(this.direction);
//            if (nextLocation.x >= TetrisUtils.PLAYFIELD_WIDTH) {
//                nextLocation.x = 0;
//            }
//            if (nextLocation.y >= TetrisUtils.PLAYFIELD_HEIGHT) {
//                nextLocation.y = 0;
//            }
//            if (nextLocation.x < 0) {
//                nextLocation.x = TetrisUtils.PLAYFIELD_WIDTH;
//            }
//            if (nextLocation.y < 0) {
//                nextLocation.y = TetrisUtils.PLAYFIELD_HEIGHT;
//            }
//            if (this.direction != Direction.NONE) {
//                this.tail.addFirst(this.head);
//                if (this.tail.size() > this.length) {
//                    this.tail.removeLast();
//                }
//                this.head = nextLocation;
//            }

//            handleCollisions(blocks);
        }
    }

    private void handleCollisions(Collection<TetrisBlock> blocks) throws Exception {
        for (TetrisBlock block : blocks) {
            boolean headCollision = this.id != block.id && block.getHead().equals(this.head);
            boolean tailCollision = block.getBlockParts().contains(this.head);
            if (headCollision || tailCollision) {
                kill();
                if (this.id != block.id) {
                    block.reward();
                }
            }
        }
    }

    public Location getHead() {
        synchronized (this.monitor) {
            return this.head;
        }
    }

    public Collection<Location> getBlockParts() {
        synchronized (this.monitor) {
            return this.tail;
        }
    }

    public void setDirection(Direction direction) {
        synchronized (this.monitor) {
            this.direction = direction;
        }
    }

    public String getLocationsJson() {
        synchronized (this.monitor) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("{x: %d, y: %d}", this.head.x, this.head.y));
            for (Location location : this.tail) {
                sb.append(',');
                sb.append(String.format("{x: %d, y: %d}", location.x, location.y));
            }
            return String.format("{'id':%d,'body':[%s]}", this.id, sb.toString());
        }
    }

    public int getId() {
        return this.id;
    }

    public String getHexColor() {
        return this.hexColor;
    }

    protected Location addBodyPart(Collection<Location> bodyParts, Location nextPart, Direction direction) {
        nextPart = nextPart.getAdjacentLocation(direction);
        bodyParts.add(nextPart);

        return nextPart;
    }

    protected Location addHeadPart(Collection<Location> tail, Location head, Direction direction) {
        Location nextLocation = head.getAdjacentLocation(direction);
        tail.add(nextLocation);

        return nextLocation;
    }
}