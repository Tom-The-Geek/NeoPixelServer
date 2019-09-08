package me.geek.tom.objsync.threads.server.event;

import me.geek.tom.objsync.packets.Packet;

public interface ServerPacketHandler {
    void onPacket(Packet packet);
}
