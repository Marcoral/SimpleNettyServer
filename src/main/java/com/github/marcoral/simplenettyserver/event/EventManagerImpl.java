package com.github.marcoral.simplenettyserver.event;

import com.github.marcoral.simplenettyserver.api.event.EventManager;
import com.github.marcoral.simplenettyserver.api.event.abst.Event;
import com.github.marcoral.simplenettyserver.api.event.concrete.NewConnectionEvent;
import com.github.marcoral.simplenettyserver.api.event.concrete.RemovedConnectionEvent;
import com.github.marcoral.simplenettyserver.api.event.concrete.ServerShutdownEvent;
import com.github.marcoral.simplenettyserver.api.event.handlers.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventManagerImpl implements EventManager {
    private Map<Class<? extends Event>, Collection<EventHandler>> eventHandlers = new HashMap<>();

    @Override
    public void invokeEvent(Event event) {
        Collection<EventHandler> handlers = eventHandlers.get(event.getEventIdentifier());
        if(handlers == null)
            return;

        for(EventHandler handler : handlers) {
            handler.on(event);
            if(event.isConsumed())
                return;
        }
    }

    @Override
    public void addEventHandler(NewConnectionEventHandler eventHandler) {
        addEventHandler(NewConnectionEvent.class, eventHandler);
    }

    @Override
    public void removeEventHandler(NewConnectionEventHandler eventHandler) {
        removeEventHandler(NewConnectionEvent.class, eventHandler);
    }

    @Override
    public void addEventHandler(RemovedConnectionEventHandler eventHandler) {
        addEventHandler(RemovedConnectionEvent.class, eventHandler);
    }

    @Override
    public void removeEventHandler(RemovedConnectionEventHandler eventHandler) {
        removeEventHandler(RemovedConnectionEvent.class, eventHandler);
    }

    @Override
    public void addEventHandler(ServerShutdownEventHandler eventHandler) {
        addEventHandler(ServerShutdownEvent.class, eventHandler);
    }

    @Override
    public void removeEventHandler(ServerShutdownEventHandler eventHandler) {
        removeEventHandler(ServerShutdownEvent.class, eventHandler);
    }

    @Override
    public void addEventHandler(CustomEventHandler eventHandler) {
        addEventHandler(eventHandler.getCustomEventClass(), eventHandler);
    }

    @Override
    public void removeEventHandler(CustomEventHandler eventHandler) {
        removeEventHandler(eventHandler.getCustomEventClass(), eventHandler);
    }

    private <T extends Event> void addEventHandler(Class<T> eventClass, EventHandler<T> handler) {
        eventHandlers.computeIfAbsent(eventClass, c -> new ArrayList<>()).add(handler);
    }

    private <T extends Event> void removeEventHandler(Class<T> eventClass, EventHandler<T> handler) {
        Collection<EventHandler> handlers = eventHandlers.get(eventClass);
        if(handlers == null)
            return;
        if(handlers.remove(handler) && handlers.size() == 0)
            eventHandlers.remove(eventClass);
    }
}