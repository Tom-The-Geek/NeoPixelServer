package me.geek.tom.objsync.threads.server;

import me.geek.tom.objsync.packets.Packet;
import me.geek.tom.objsync.packets.PacketRegistry;
import me.geek.tom.objsync.threads.server.event.ServerPacketEventManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.logging.Logger;

@SuppressWarnings("PrimitiveArrayArgumentToVarargsMethod")
public class ServerClientConnectionThread extends Thread {

    private DataOutputStream serverOutput;
    private DataInputStream serverInput;

    private final ServerConnectionThread owner;

    private static final Logger LOGGER = Logger.getLogger(ServerClientConnectionThread.class.getName());

    public ServerClientConnectionThread(Socket socket, ServerConnectionThread owner) {
        this.owner = owner;
        try {
            serverInput  = new DataInputStream(socket.getInputStream());
            serverOutput = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setName("Server -> client connection thread");
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] packetId = new byte[2];
                serverInput.readFully(packetId, 0, 2);
                int length = serverInput.readInt();
                if (length > 0) {
                    byte[] packetData = new byte[length];

                    serverInput.readFully(packetData, 0, length);

                    Packet packet = decodePacket(packetId, packetData);

                    ServerPacketEventManager.INSTANCE.onPacket(packet);
                }
            }
        } catch (IOException e) {
            LOGGER.info("A client has disconnected?");
        }
        owner.getThreads().remove(this);
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

        byte length_byte = ((Integer) length).byteValue();

        serverOutput.write(packetId);
        serverOutput.write(length_byte);
        serverOutput.write(data);
        serverOutput.flush();
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
