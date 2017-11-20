package me.david.messagecore.netwok;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/*
 * Decodes a incoming Packet
 */
public class Decoder extends ByteToMessageDecoder {

    private List<Class<? extends Packet>> inpackets;

    public Decoder setInpackets(List<Class<? extends Packet>> inpackets) {
        this.inpackets = inpackets;
        return this;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> output) throws Exception {
        PacketSerializer ps = new PacketSerializer(bytebuf);
        int id = ps.readVarInt();
        Class<? extends Packet> packetClass = inpackets.get(id);
        if(packetClass == null) throw new NullPointerException("Coud not find that Packet");
        Packet p = packetClass.newInstance();
        p.read(ps);
        output.add(p);
    }
}
