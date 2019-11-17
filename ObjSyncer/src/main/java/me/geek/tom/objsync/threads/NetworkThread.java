package me.geek.tom.objsync.threads;

import me.geek.tom.objsync.threads.client.ClientThread;
import me.geek.tom.objsync.threads.server.ServerConnectionThread;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class NetworkThread extends Thread {

    private final Mode mode;
    private String host;
    private int port;

    private Thread connectionThread;
    private boolean active;

    public Thread getConnectionThread() {
        return connectionThread;
    }

    public NetworkThread(Mode mode, String host, int port) {
        this.mode = mode;
        this.active = false;
        this.host = host;
        this.port = port;
    }

    public NetworkThread(Mode mode) {
        this(mode, "127.0.0.1", 9845);
    }

    @Override
    public void run() {
        if (this.mode.equals(Mode.CLIENT)) {
            // this.connectionThread = new ClientThread("192.168.1.115", 9845);
            System.out.println("client setting up...");
            this.connectionThread = new ClientThread(this.host, this.port);
        } else if (this.mode.equals(Mode.SERVER)) {
            System.out.println("server setting up...");
            this.connectionThread = new ServerConnectionThread(this.port);
        }
        System.out.println("starting child thread...");
        this.connectionThread.start();
        System.out.println("setting active flag!");
        this.active = true;
    }

    public enum Mode {
        SERVER,CLIENT
    }

    public boolean isReady() {
        return this.active;
    }
}
