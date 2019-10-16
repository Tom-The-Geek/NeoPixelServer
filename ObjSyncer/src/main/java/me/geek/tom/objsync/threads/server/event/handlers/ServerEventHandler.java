package me.geek.tom.objsync.threads.server.event.handlers;

import me.geek.tom.objsync.threads.server.event.Event;

public interface ServerEventHandler {
    void onEvent(Event e);
}
