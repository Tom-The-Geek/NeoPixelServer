package me.geek.tom.remoteneoparticle.networking;

import me.geek.tom.objsync.packets.Packet;
import me.geek.tom.objsync.packets.PacketRegistry;
import me.geek.tom.objsync.threads.NetworkThread;
import me.geek.tom.objsync.threads.client.ClientThread;
import me.geek.tom.remoteneoparticle.networking.packets.StripSetColourPacket;
import me.geek.tom.remoteneoparticle.networking.packets.StripShowPacket;

import java.io.IOException;
import java.util.Arrays;

public class ClientNetworkingManager {

    private static ClientNetworkingManager INSTANCE;

    private NetworkThread networkthread;

    public static ClientNetworkingManager getInstance() {
        System.out.println("Get instance!");
        if (INSTANCE == null) {
            System.out.println("Create instance");
            INSTANCE = new ClientNetworkingManager();
        }
        return INSTANCE;
    }

    private ClientNetworkingManager() {
        System.out.println("Creating network thread...");
        this.networkthread = new NetworkThread(NetworkThread.Mode.CLIENT, "192.168.1.134", 10002);
        System.out.println("Done! Wait for ready");
        this.networkthread.start();
        // while (!this.networkthread.isReady()) {}
        System.out.println("Ready! Registering packets...");
        try {
            PacketRegistry.registerPacket("sc".getBytes(), StripSetColourPacket.class);
            PacketRegistry.registerPacket("ss".getBytes(), StripShowPacket.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("setup complete");
        try {
            System.out.println("wait for client connection");
            Thread.sleep(1000); // Wait for client connection...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendStripPacket(Packet packet) {
        try {
            System.out.println("send packet: " + Arrays.toString(packet.getId()) + Arrays.toString(packet.toBytes()));
            ((ClientThread) this.networkthread.getConnectionThread()).sendPacket(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
