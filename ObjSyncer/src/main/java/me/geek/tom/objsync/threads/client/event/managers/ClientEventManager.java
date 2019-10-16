package me.geek.tom.objsync.threads.client.event.managers;

import com.google.common.collect.Lists;
import me.geek.tom.objsync.threads.client.event.Event;
import me.geek.tom.objsync.threads.client.event.handlers.ClientEventHandler;

import java.util.List;

public class ClientEventManager {
    public static ClientEventManager INSTANCE = new ClientEventManager();

    private List<ClientEventHandler> handlers = Lists.newArrayList();

    public void triggerEvent(Event event) {
        for (ClientEventHandler handler : this.handlers) {
            handler.onEvent(event);
        }
    }

    public void addHandler(ClientEventHandler handler) {
        this.handlers.add(handler);
    }
}
