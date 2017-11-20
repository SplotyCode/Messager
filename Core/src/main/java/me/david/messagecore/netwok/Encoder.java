package me.david.messagecore.netwok;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/*
 * Incodes a outgoing Packet
 */
public class Encoder extends MessageToByteEncoder<Packet> {

    private List<Class<? extends Packet>> outpackets;

    public Encoder setOutpackets(List<Class<? extends Packet>> outpackets) {
        this.outpackets = outpackets;
        return this;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf output) throws Exception {
        int id = outpackets.indexOf(packet.getClass());
        if(id == -1) throw new NullPointerException("Coud not find id to packet: " + packet.getClass().getSimpleName());
        PacketSerializer ps = new PacketSerializer(output);
        ps.writeVarInt(id);
        packet.write(ps);
    }
}
