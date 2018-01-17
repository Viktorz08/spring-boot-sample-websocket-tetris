package com.hotmail.viktorz08.test.spring.testris.client;

import com.hotmail.viktorz08.test.spring.testris.block.TetrisBlock;
import org.springframework.web.socket.WebSocketSession;

public interface Client {
    int getId();

    WebSocketSession getSession();

    void setSession(WebSocketSession session);

    TetrisBlock getTetrisBlock();

    void setTetrisBlock(TetrisBlock tetrisBlock);
}
