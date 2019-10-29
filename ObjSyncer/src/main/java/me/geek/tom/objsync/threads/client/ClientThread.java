package me.geek.tom.objsync.threads.client;

import me.geek.tom.objsync.packets.Packet;
import me.geek.tom.objsync.packets.PacketRegistry;
import me.geek.tom.objsync.threads.client.event.ConnectedEvent;
import me.geek.tom.objsync.threads.client.event.LostConnectionEvent;
import me.geek.tom.objsync.threads.client.event.managers.ClientEventManager;
import me.geek.tom.objsync.threads.client.event.managers.ClientPacketEventManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.logging.Logger;

@SuppressWarnings({"unused", "PrimitiveArrayArgumentToVarargsMethod"})
public class ClientThread extends Thread {

    private Socket socket;
    private DataOutputStream clientOutput;

    private final Logger LOGGER = Logger.getLogger(ClientThread.class.getName());

    public ClientThread(String host, int port) {
        super();
        this.setName("Client network thread");
        try {
            this.socket = new Socket(host, port);
            LOGGER.info("Client connected!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (!this.socket.isConnected()) {
            return;
        }

        LOGGER.info("Creating data streams...");

        DataInputStream clientInput;
        try {
            this.clientOutput = new DataOutputStream(this.socket.getOutputStream());
            clientInput = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        LOGGER.info("Done! Main loop!");

        ClientEventManager.INSTANCE.triggerEvent(new ConnectedEvent());

        while (!this.socket.isClosed()) {
            try {
                LOGGER.info("Reading id bytes...");
                byte[] packetId = new byte[2];
                clientInput.readFully(packetId, 0, 2);
                LOGGER.info("Got id: " + new String(packetId) + "Reading length...");
                byte[] length_byte = new byte[1];
                int length;
                clientInput.readFully(length_byte, 0, 1);
                length = length_byte[0];
                LOGGER.info("Got length: " + length + "! Reading data bytes...");
                byte[] data = new byte[length];
                clientInput.readFully(data, 0, length);
                LOGGER.info("Decoding packet...");
                Packet packet = this.decodePacket(packetId, data);
                LOGGER.info("Packet decoded! Triggering handler...");
                ClientPacketEventManager.INSTANCE.onPacket(packet);
                LOGGER.info("DONE!");
            } catch (IOException e) {
                e.printStackTrace();
                ClientEventManager.INSTANCE.triggerEvent(new LostConnectionEvent());
                return;
            }
        }
        ClientEventManager.INSTANCE.triggerEvent(new LostConnectionEvent());
    }

    public void sendPacket(Packet packet) throws IOException, IllegalStateException {
        int length = packet.getLength();
        byte[] data = packet.toBytes();
        if (data.length != length) {
            throw new IllegalStateException("Packet length and data length do not match! This is not allowed!");
        }
        byte[] packetId = packet.getId();
        if (packetId.length != 2) {
            throw new IllegalStateException("Packet id must be exactly two bytes!");
        }

        this.clientOutput.write(packetId);
        this.clientOutput.flush();
        this.clientOutput.writeInt(length);
        this.clientOutput.flush();
        this.clientOutput.write(data);
        this.clientOutput.flush();
    }

    private Packet decodePacket(byte[] packetId, byte[] data) {
        try {
            return (Packet) PacketRegistry.queryPacket(packetId).getMethod("fromBytes", byte[].class).invoke(null, data);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
