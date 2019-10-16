package me.geek.tom.objsync.test;

import me.geek.tom.objsync.packets.Packet;
import me.geek.tom.objsync.packets.PacketRegistry;
import me.geek.tom.objsync.threads.NetworkThread;
import me.geek.tom.objsync.threads.server.ServerClientConnectionThread;
import me.geek.tom.objsync.threads.server.ServerConnectionThread;
import me.geek.tom.objsync.threads.server.event.managers.EventManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@SuppressWarnings({"LoopConditionNotUpdatedInsideLoop"})
public class TestServer {

    private static final Logger LOGGER = Logger.getLogger(TestServer.class.getName());

    static {
        InputStream stream = TestServer.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) {

        try {
            PacketRegistry.registerPacket("tt".getBytes(), TestPacket.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        PacketRegistry.freezeRegistry(true);

        EventManager.INSTANCE.addHandler((event) -> LOGGER.info("An event has been triggered! " + event.getClass().getName()));

        NetworkThread networkThread = new NetworkThread(NetworkThread.Mode.SERVER);
        networkThread.start();
        while (!networkThread.isReady()) {
            // LOGGER.info(".");
        }

        ServerConnectionThread serverThread = (ServerConnectionThread) networkThread.getConnectionThread();

        Scanner scanner = new Scanner(System.in);

         while (true) {
            LOGGER.info("Text:");
            String packet = scanner.nextLine();

            Packet p = new TestPacket(packet);
            for (Thread t : serverThread.getThreads()) {
                ServerClientConnectionThread thread = (ServerClientConnectionThread) t;

                try {
                    thread.sendPacket(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
