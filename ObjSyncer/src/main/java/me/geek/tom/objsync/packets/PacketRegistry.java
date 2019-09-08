package me.geek.tom.objsync.packets;

import me.geek.tom.objsync.util.Util;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {
    private static Map<byte[], Class<? extends Packet>> packetIds = new HashMap<>();
    private static boolean isFrozen = false;

    public static boolean isFrozen() {
        return isFrozen;
    }

    public static void freezeRegistry() {
        if (!isFrozen) {
            isFrozen = true;
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
    }

    public static Class<? extends Packet> queryPacket(byte[] packetId) {
        if (packetId.length != 2) {
            throw new IllegalArgumentException("Packet ID must have a length of two!");
        }

        if (!packetIds.containsKey(packetId)) {
            throw new IllegalStateException("Invalid packet with ID: " + Util.bytesToHex(packetId));
        }

        return packetIds.get(packetId);
    }
}
