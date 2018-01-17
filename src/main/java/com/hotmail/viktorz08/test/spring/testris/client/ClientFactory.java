package com.hotmail.viktorz08.test.spring.testris.client;

import com.hotmail.viktorz08.test.spring.testris.client.impl.Gamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ClientFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ClientFactory.class);

    private static final AtomicInteger clientIds = new AtomicInteger(0);

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static Client createClient() {
        int id = clientIds.getAndIncrement();
        return new Gamer(id);
    }
}