package me.geek.tom.objsync.threads.server.event.managers;

import com.google.common.collect.Lists;
import me.geek.tom.objsync.packets.Packet;
import me.geek.tom.objsync.threads.server.event.handlers.ServerPacketHandler;

import java.util.List;

@SuppressWarnings("unused")
public class ServerPacketEventManager {
    public static final ServerPacketEventManager INSTANCE = new ServerPacketEventManager();

    private final List<ServerPacketHandler> handlers = Lists.newArrayList();

    public void onPacket(Packet packet) {
        for (ServerPacketHandler handler : this.handlers) {
            handler.onPacket(packet);
        }
    }

    public void registerListener(ServerPacketHandler handler) {
        this.handlers.add(handler);
    }
}
