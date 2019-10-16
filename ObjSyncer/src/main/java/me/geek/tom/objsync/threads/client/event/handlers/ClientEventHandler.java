package me.geek.tom.objsync.threads.client.event.handlers;

import me.geek.tom.objsync.threads.client.event.Event;

public interface ClientEventHandler {
    void onEvent(Event event);
}
