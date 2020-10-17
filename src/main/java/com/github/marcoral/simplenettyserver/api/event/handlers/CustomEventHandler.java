package com.github.marcoral.simplenettyserver.api.event.handlers;

import com.github.marcoral.simplenettyserver.api.event.abst.CustomEvent;

public interface CustomEventHandler<T extends CustomEvent> extends EventHandler<T> {
    Class<T> getCustomEventClass();
}