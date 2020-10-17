package com.github.marcoral.simplenettyserver.api.packet;

public class InvalidPacketError implements PacketOut {
    public static final int PACKET_ID = Integer.MAX_VALUE;  //Magic value. Don't change it, client's code uses the same.

    @Override
    public int getPacketID() {
        return PACKET_ID;
    }

    private final String cause;
    public InvalidPacketError() {
        this.cause = "Invalid packet";
    }

    public InvalidPacketError(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}