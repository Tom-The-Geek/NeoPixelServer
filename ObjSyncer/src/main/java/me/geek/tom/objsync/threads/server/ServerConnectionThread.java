package me.geek.tom.objsync.threads.server;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class ServerConnectionThread extends Thread {

    private ServerSocket socket;
    private static Logger LOGGER = Logger.getLogger(ServerConnectionThread.class.getName());
    private ServerClientConnectionThread[] clientTreads;

    public List<Thread> getThreads() {
        return threads;
    }

    private List<Thread> threads = Lists.newArrayList();

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
                Thread t = new ServerClientConnectionThread(s);
                t.start();
                threads.add(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
