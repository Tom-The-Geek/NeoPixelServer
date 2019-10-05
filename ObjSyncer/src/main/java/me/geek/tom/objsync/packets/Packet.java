package me.geek.tom.objsync.packets;

@SuppressWarnings("unused")
public abstract class Packet {
    public abstract byte[] toBytes();
    public abstract byte[] getId();
    public abstract int getLength();
    @SuppressWarnings("SameReturnValue")
    public static Packet fromBytes(byte[] bytes) {
        return null;
    }
}
