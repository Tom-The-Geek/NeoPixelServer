package me.geek.tom.objsync.test;

import me.geek.tom.objsync.packets.Packet;
import me.geek.tom.objsync.packets.PacketRegistry;
import me.geek.tom.objsync.threads.NetworkThread;
import me.geek.tom.objsync.threads.server.ServerClientConnectionThread;
import me.geek.tom.objsync.threads.server.ServerConnectionThread;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

@SuppressWarnings({"LoopConditionNotUpdatedInsideLoop", "WeakerAccess"})
public class TestServer {

    private static final Logger LOGGER = Logger.getLogger(TestServer.class.getName());

    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) {

        try {
            PacketRegistry.registerPacket("tt".getBytes(), TestPacket.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        PacketRegistry.freezeRegistry(true);

        NetworkThread networkThread = new NetworkThread(NetworkThread.Mode.SERVER);
        networkThread.start();
        while (networkThread.isReady()) {
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
