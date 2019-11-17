package me.geek.tom.remoteneoparticle.networking;

import me.geek.tom.objsync.packets.PacketRegistry;
import me.geek.tom.objsync.threads.NetworkThread;
import me.geek.tom.objsync.threads.server.event.handlers.ServerPacketHandler;
import me.geek.tom.objsync.threads.server.event.managers.ServerPacketEventManager;
import me.geek.tom.remoteneoparticle.networking.packets.StripSetColourPacket;
import me.geek.tom.remoteneoparticle.networking.packets.StripShowPacket;

@SuppressWarnings("StatementWithEmptyBody")
public class ServerNetworkingManager {

    private static ServerNetworkingManager INSTANCE;

    private NetworkThread networkThread;

    public static ServerNetworkingManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerNetworkingManager();
        }
        return INSTANCE;
    }

    private ServerNetworkingManager() {
        this.networkThread = new NetworkThread(NetworkThread.Mode.SERVER, "0.0.0.0", 10002);
        this.networkThread.start();
        while (!this.networkThread.isReady()) {}
        // Initialise packet registry
        try {
            PacketRegistry.registerPacket("sc".getBytes(), StripSetColourPacket.class);
            PacketRegistry.registerPacket("ss".getBytes(), StripShowPacket.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void addHandler(ServerPacketHandler handler) {
        ServerPacketEventManager.INSTANCE.registerListener(handler);
    }
}
