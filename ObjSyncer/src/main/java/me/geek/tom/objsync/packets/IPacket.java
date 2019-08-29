package me.geek.tom.objsync.packets;

public interface IPacket {
    byte[] toPacket();
    void fromPacket(byte[] packetData);
}
