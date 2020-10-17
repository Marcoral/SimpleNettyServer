package com.github.marcoral.simplenettyserver.api.event.abst;

public class ConsumableEventBase implements Event {
    private boolean isConsumed;

    @Override
    public void consume() {
        isConsumed = true;
    }

    @Override
    public boolean isConsumed() {
        return isConsumed;
    }
}