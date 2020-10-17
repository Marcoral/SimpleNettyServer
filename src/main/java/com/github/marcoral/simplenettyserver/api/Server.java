package com.github.marcoral.simplenettyserver.api;

import com.github.marcoral.simplenettyserver.api.event.EventManager;

import java.util.concurrent.Future;

public interface Server {
    Future<?> shutdown();
    EventManager getEventManager();
}