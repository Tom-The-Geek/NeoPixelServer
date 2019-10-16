package me.geek.tom.objsync.threads.server.event.managers;

import com.google.common.collect.Lists;
import me.geek.tom.objsync.threads.server.event.Event;
import me.geek.tom.objsync.threads.server.event.handlers.ServerEventHandler;

import java.util.List;

public class EventManager {
    public static EventManager INSTANCE = new EventManager();

    private List<ServerEventHandler> handlers = Lists.newArrayList();

    public void triggerEvent(Event event) {
        for (ServerEventHandler handler : this.handlers) {
            handler.onEvent(event);
        }
    }

    public void addHandler(ServerEventHandler handler) {
        this.handlers.add(handler);
    }
}
