package me.geek.tom.objsync.threads.server.event;

import java.net.InetAddress;

public class ClientDisconnectEvent extends Event {
    private InetAddress address;

    public ClientDisconnectEvent(InetAddress address) {
        this.address = address;
    }

    public InetAddress getAddress() {
        return address;
    }
}
