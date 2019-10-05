package me.geek.tom.objsync.threads.client.event;

import com.google.common.collect.Lists;
import me.geek.tom.objsync.packets.Packet;

import java.util.List;

public class ClientPacketEventManager {
    public static final ClientPacketEventManager INSTANCE = new ClientPacketEventManager();

    private final List<ClientPacketHandler> handlers = Lists.newArrayList();

    public void onPacket(Packet packet) {
        for (ClientPacketHandler handler : this.handlers) {
            handler.onPacket(packet);
        }
    }

    public void registerListener(ClientPacketHandler handler) {
        this.handlers.add(handler);
    }
}
