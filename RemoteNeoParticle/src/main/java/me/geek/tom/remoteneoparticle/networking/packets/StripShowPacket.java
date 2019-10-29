package me.geek.tom.remoteneoparticle.networking.packets;

import me.geek.tom.objsync.packets.Packet;

public class StripShowPacket extends Packet {

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public byte[] getId() {
        return "ss".getBytes();
    }

    @Override
    public int getLength() {
        return 0;
    }

    public static Packet fromBytes(byte[] bytes) {
        return null;
    }
}
