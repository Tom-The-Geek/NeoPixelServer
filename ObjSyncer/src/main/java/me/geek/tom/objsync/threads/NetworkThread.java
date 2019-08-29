package me.geek.tom.objsync.threads;

import java.net.Socket;

public class NetworkThread extends Thread {

    private Socket socket;
    private Mode mode;

    public NetworkThread(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void run() {
        // Code not implemented yet
        System.out.println("This is a test network thread");
    }

    public enum Mode {
        SERVER,CLIENT
    }
}
