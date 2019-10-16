package me.geek.tom.objsync.threads.server.event;

import java.net.InetAddress;

public class ClientConnectedEvent extends Event {

    private InetAddress address;

    public ClientConnectedEvent(InetAddress address) {
        this.address = address;
    }

    public InetAddress getAddress() {
        return address;
    }
}
