package me.geek.tom.objsync.threads.server.event.handlers;

import me.geek.tom.objsync.packets.Packet;

public interface ServerPacketHandler {
    @SuppressWarnings("unused")
    void onPacket(Packet packet);
}
