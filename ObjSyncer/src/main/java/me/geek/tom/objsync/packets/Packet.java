package me.geek.tom.objsync.packets;

public abstract class Packet {
    public abstract byte[] toBytes();
    public abstract byte[] getId();
    public abstract int getLength();
}
