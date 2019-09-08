package me.geek.tom.objsync.threads;

import me.geek.tom.objsync.threads.client.ClientThread;
import me.geek.tom.objsync.threads.server.ServerConnectionThread;

import java.net.Socket;

public class NetworkThread extends Thread {

    private Socket socket;
    private Mode mode;

    public Thread getConnectionThread() {
        return connectionThread;
    }

    private Thread connectionThread;
    private boolean active;

    public NetworkThread(Mode mode) {
        this.mode = mode;
        this.active = false;
    }

    @Override
    public void run() {
        if (this.mode.equals(Mode.CLIENT)) {
            this.connectionThread = new ClientThread("localhost", 9845);
        } else if (this.mode.equals(Mode.SERVER)) {
            this.connectionThread = new ServerConnectionThread();
        }
        this.connectionThread.start();
        this.active = true;
    }

    public enum Mode {
        SERVER,CLIENT
    }

    public boolean isReady() {
        return this.active;
    }
}
