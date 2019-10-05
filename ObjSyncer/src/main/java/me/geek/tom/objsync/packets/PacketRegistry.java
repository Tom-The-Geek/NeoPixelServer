package me.geek.tom.objsync.packets;

import me.geek.tom.objsync.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PacketRegistry {
    private static final Map<byte[], Class<? extends Packet>> packetIds = new HashMap<>();
    private static boolean isFrozen = false;
    private static final Logger LOGGER = Logger.getLogger(PacketRegistry.class.getName());

    @SuppressWarnings("unused")
    public static boolean isFrozen() {
        return isFrozen;
    }

    public static void freezeRegistry(boolean doRegistryDump) {
        if (!isFrozen) {
            isFrozen = true;
        }
        if (doRegistryDump){
            LOGGER.info("REGISTRY DUMP!");
            for (byte[] id : packetIds.keySet()) {
                LOGGER.info("PACKET WITH ID: " + Util.bytesToHex(id) + " MAPS TO: " + packetIds.get(id).getName());
            }
        }
    }

    public static void registerPacket(byte[] packetId, Class<? extends Packet> packetType) throws IllegalAccessException, IllegalArgumentException {
        if (isFrozen) {
            throw new IllegalAccessException("Cannot register packet after registry is frozen");
        }
        if (packetId.length != 2) {
            throw new IllegalArgumentException("Packet ID must have a length of two!");
        }

        packetIds.put(packetId, packetType);
        LOGGER.info("PACKET REGISTERED WITH ID: " + Util.bytesToHex(packetId));
    }

    public static Class<? extends Packet> queryPacket(byte[] packetId) {
        LOGGER.info("Querying packet with ID: " + Util.bytesToHex(packetId));
        if (packetId.length != 2) {
            throw new IllegalArgumentException("Packet ID must have a length of two!");
        }

        for (byte[] packet : packetIds.keySet()) {
            boolean yep = true;
            for (int i = 0; i < packet.length; i++) {
                if (packet[i] != packetId[i]) {
                    yep = false;
                    break;
                }
            }
            if (yep) {
                return packetIds.get(packet);
            }
        }

        throw new IllegalArgumentException("Packet ID: " + Util.bytesToHex(packetId) + " not found in registry!");
    }
}
