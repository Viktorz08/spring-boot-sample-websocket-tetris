package com.hotmail.viktorz08.test.spring.testris.client.impl;

import com.hotmail.viktorz08.test.spring.testris.client.AbstractClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gamer extends AbstractClient {
    private static final Logger LOG = LoggerFactory.getLogger(Gamer.class);


    public Gamer(int id) {
        super(id);
    }
}