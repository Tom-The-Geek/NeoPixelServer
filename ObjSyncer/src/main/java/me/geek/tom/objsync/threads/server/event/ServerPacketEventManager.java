package me.geek.tom.objsync.threads.server.event;

import com.google.common.collect.Lists;
import me.geek.tom.objsync.packets.Packet;
import me.geek.tom.objsync.threads.client.event.ClientPacketHandler;

import java.util.List;

public class ServerPacketEventManager {
    public static ServerPacketEventManager INSTANCE = new ServerPacketEventManager();

    private List<ServerPacketHandler> handlers = Lists.newArrayList();

    public void onPacket(Packet packet) {
        for (ServerPacketHandler handler : this.handlers) {
            handler.onPacket(packet);
        }
    }

    public void registerListener(ServerPacketHandler handler) {
        this.handlers.add(handler);
    }
}
