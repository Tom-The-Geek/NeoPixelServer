package me.geek.tom.remoteneoparticle.networking;

import me.geek.tom.objsync.packets.Packet;
import me.geek.tom.objsync.packets.PacketRegistry;
import me.geek.tom.objsync.threads.NetworkThread;
import me.geek.tom.objsync.threads.client.ClientThread;
import me.geek.tom.remoteneoparticle.networking.packets.StripSetColourPacket;
import me.geek.tom.remoteneoparticle.networking.packets.StripShowPacket;

import java.io.IOException;

@SuppressWarnings("StatementWithEmptyBody")
public class ClientNetworkingManager {

    private static ClientNetworkingManager INSTANCE;

    private NetworkThread networkthread;

    public static ClientNetworkingManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientNetworkingManager();
        }
        return INSTANCE;
    }

    private ClientNetworkingManager() {
        this.networkthread = new NetworkThread(NetworkThread.Mode.CLIENT, "127.0.0.1", 10002);
        while (!this.networkthread.isReady()) {}
        try {
            PacketRegistry.registerPacket("sc".getBytes(), StripSetColourPacket.class);
            PacketRegistry.registerPacket("ss".getBytes(), StripShowPacket.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void sendStripPacket(Packet packet) {
        try {
            ((ClientThread) this.networkthread.getConnectionThread()).sendPacket(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
