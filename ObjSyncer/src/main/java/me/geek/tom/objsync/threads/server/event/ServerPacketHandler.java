package me.geek.tom.objsync.threads.server.event;

import me.geek.tom.objsync.packets.Packet;

@SuppressWarnings("WeakerAccess")
public interface ServerPacketHandler {
    @SuppressWarnings("unused")
    void onPacket(Packet packet);
}
