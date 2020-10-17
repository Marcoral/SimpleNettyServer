package com.github.marcoral.simplenettyserver.api.event.abst;

public interface Event {
    void consume();
    boolean isConsumed();

    default Class<? extends Event> getEventIdentifier() {
        return getClass();
    }
}