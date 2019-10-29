package me.geek.tom.remoteneoparticle.networking.packets;

import com.github.mbelling.ws281x.Color;
import me.geek.tom.objsync.packets.Packet;

import java.nio.ByteBuffer;

@SuppressWarnings("WeakerAccess")
public class StripSetColourPacket extends Packet {

    private int r;
    private int g;
    private int b;
    private int pos;

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getPos() {
        return pos;
    }

    public StripSetColourPacket(int pos, Color col) {
        this(pos, col.getRed(), col.getGreen(), col.getBlue());
    }

    public StripSetColourPacket(int pos, int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.pos = pos;
    }

    public static Packet fromBytes(byte[] bytes) {
        byte[] r = new byte[4];
        byte[] g = new byte[4];
        byte[] b = new byte[4];
        byte[] pos = new byte[4];
        for (int i = 0; i < bytes.length; i++) {
            if (i < 4) {
                r[i] = bytes[i];
            } else if (i < 8) {
                g[i - 4] = bytes[i];
            } else if (i < 12) {
                b[i - 8] = bytes[i];
            } else if (i < 16) {
                pos[i - 12] = bytes[i];
            }
        }
        int r_int = ByteBuffer.wrap(r).getInt();
        int g_int = ByteBuffer.wrap(g).getInt();
        int b_int = ByteBuffer.wrap(b).getInt();
        int pos_int = ByteBuffer.wrap(pos).getInt();
        return new StripSetColourPacket(pos_int, r_int, g_int, b_int);
    }

    @Override
    public byte[] toBytes() {
        return ByteBuffer.allocate(16).putInt(this.r).putInt(this.g).putInt(this.b).putInt(this.pos).array();
    }

    @Override
    public byte[] getId() {
        return "sc".getBytes();
    }

    @Override
    public int getLength() {
        return this.toBytes().length;
    }
}
