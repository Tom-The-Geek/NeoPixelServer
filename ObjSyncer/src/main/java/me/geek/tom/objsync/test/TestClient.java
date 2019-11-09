package me.geek.tom.objsync.test;

import me.geek.tom.objsync.packets.PacketRegistry;
import me.geek.tom.objsync.threads.NetworkThread;
import me.geek.tom.objsync.threads.client.ClientThread;
import me.geek.tom.objsync.threads.client.event.managers.ClientEventManager;
import me.geek.tom.objsync.threads.client.event.managers.ClientPacketEventManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class TestClient {

    private static Logger LOGGER = Logger.getLogger(TestClient.class.getName());

    static {
        InputStream stream = TestClient.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ClientPacketEventManager.INSTANCE.registerListener(p -> LOGGER.info(((TestPacket) p).getData()));

        ClientEventManager.INSTANCE.addHandler((event -> {
            LOGGER.info("An event has been triggered! " + event.getClass().getName());
        }));

        try {
            PacketRegistry.registerPacket("tt".getBytes(), TestPacket.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        PacketRegistry.freezeRegistry(true);

        NetworkThread networkThread = new NetworkThread(NetworkThread.Mode.CLIENT);
        networkThread.start();

        while (!networkThread.isReady()) {}

        // ClientThread clientThread = (ClientThread) networkThread.getConnectionThread();
    }
}
