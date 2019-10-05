package me.geek.tom.objsync.test;

        import me.geek.tom.objsync.packets.Packet;

@SuppressWarnings("unused")
public class TestPacket extends Packet {

    public String getData() {
        return data;
    }

    private final String data;

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

    public static Packet fromBytes(byte[] bytes) {
        return new TestPacket(new String(bytes));
    }
}
