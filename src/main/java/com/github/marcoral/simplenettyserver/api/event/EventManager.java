package com.github.marcoral.simplenettyserver.api.event;

import com.github.marcoral.simplenettyserver.api.event.abst.Event;
import com.github.marcoral.simplenettyserver.api.event.handlers.CustomEventHandler;
import com.github.marcoral.simplenettyserver.api.event.handlers.NewConnectionEventHandler;
import com.github.marcoral.simplenettyserver.api.event.handlers.RemovedConnectionEventHandler;
import com.github.marcoral.simplenettyserver.api.event.handlers.ServerShutdownEventHandler;

public interface EventManager {
    void invokeEvent(Event event);

    //TODO: Add priority
    void addEventHandler(NewConnectionEventHandler eventHandler);
    void removeEventHandler(NewConnectionEventHandler eventHandler);
    void addEventHandler(RemovedConnectionEventHandler eventHandler);
    void removeEventHandler(RemovedConnectionEventHandler eventHandler);
    void addEventHandler(ServerShutdownEventHandler eventHandler);
    void removeEventHandler(ServerShutdownEventHandler eventHandler);

    void addEventHandler(CustomEventHandler eventHandler);
    void removeEventHandler(CustomEventHandler eventHandler);
}