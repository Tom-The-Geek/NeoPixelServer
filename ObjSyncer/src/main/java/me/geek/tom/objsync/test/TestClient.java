package me.geek.tom.objsync.test;

import me.geek.tom.objsync.threads.NetworkThread;
import me.geek.tom.objsync.threads.client.ClientThread;
import me.geek.tom.objsync.threads.client.event.ClientPacketEventManager;

import java.util.logging.Logger;

public class TestClient {

    private static Logger LOGGER = Logger.getLogger(TestClient.class.getName());

    public static void main(String[] args) {

        ClientPacketEventManager.INSTANCE.registerListener(p -> LOGGER.info(((TestPacket) p).getData()));

        NetworkThread networkThread = new NetworkThread(NetworkThread.Mode.CLIENT);
        networkThread.start();

        while (!networkThread.isReady()) {}

        ClientThread clientThread = (ClientThread) networkThread.getConnectionThread();
    }
}
