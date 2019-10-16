package me.geek.tom.objsync.threads.server;

import com.google.common.collect.Lists;
import me.geek.tom.objsync.threads.server.event.ClientConnectedEvent;
import me.geek.tom.objsync.threads.server.event.managers.EventManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class ServerConnectionThread extends Thread {

    private ServerSocket socket;
    private static final Logger LOGGER = Logger.getLogger(ServerConnectionThread.class.getName());

    public List<Thread> getThreads() {
        return threads;
    }

    private final List<Thread> threads = Lists.newArrayList();

    public ServerConnectionThread() {
        super();
        setName("Server listener thread");
    }

    @Override
    public void run() {
        try {
            this.socket = new ServerSocket(9845);
        } catch (IOException e) {
            LOGGER.info("Failed to bind to port:");
            e.printStackTrace();
        }

        while (!this.socket.isClosed()) {
            try {
                Socket s = this.socket.accept();
                Thread t = new ServerClientConnectionThread(s, this);
                t.start();
                threads.add(t);
                EventManager.INSTANCE.triggerEvent(new ClientConnectedEvent(s.getInetAddress()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Server thread stopped!");
    }
}
