package me.david.messagecore.netwok;

import java.io.IOException;

public interface Packet {

    void read(PacketSerializer bytebuf) throws IOException;
    void write(PacketSerializer bytebuf) throws IOException;

}


