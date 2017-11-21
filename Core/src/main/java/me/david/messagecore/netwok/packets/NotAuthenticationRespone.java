package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

public class NotAuthenticationRespone implements Packet {

    public NotAuthenticationRespone(){}

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {

    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {

    }
}
