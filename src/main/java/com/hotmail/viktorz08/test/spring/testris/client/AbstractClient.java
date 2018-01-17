package com.hotmail.viktorz08.test.spring.testris.client;

import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

public abstract class AbstractClient implements Client {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractClient.class);

    private final int id;
    @Autowired
    TetrisBlock tetrisBlock;
    private WebSocketSession session;

    public AbstractClient(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public WebSocketSession getSession() {
        return session;
    }

    @Override
    public void setSession(WebSocketSession session) {
        this.session = session;
        this.tetrisBlock.setSession(session);
    }

    public TetrisBlock getTetrisBlock() {
        return tetrisBlock;
    }

    public void setTetrisBlock(TetrisBlock tetrisBlock) {
        this.tetrisBlock = tetrisBlock;
    }
}