package me.geek.tom.objsync.test;

import me.geek.tom.objsync.packets.Packet;

public class TestPacket extends Packet {

    public String getData() {
        return data;
    }

    private String data;

    public TestPacket(String data) {
        this.data = data;
    }

    @Override
    public byte[] toBytes() {
        return this.data.getBytes();
    }

    @Override
    public byte[] getId() {
        return "tt".getBytes();
    }

    @Override
    public int getLength() {
        return this.data.length();
    }


}
