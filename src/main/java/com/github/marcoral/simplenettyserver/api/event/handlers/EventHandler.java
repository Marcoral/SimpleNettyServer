package com.github.marcoral.simplenettyserver.api.event.handlers;

import com.github.marcoral.simplenettyserver.api.event.abst.Event;

public interface EventHandler<T extends Event> {
    void on(T t);
}