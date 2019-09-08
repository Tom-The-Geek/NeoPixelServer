package me.geek.tom.objsync.threads.client.event;

import me.geek.tom.objsync.packets.Packet;

public interface ClientPacketHandler {
    void onPacket(Packet packet);
}
