package me.david.messagecore.utils;

import io.netty.channel.Channel;
import me.david.messagecore.netwok.Packet;

public final class ChannelUtil {

    //sends a packet to a channel
    public static void sendPacket(Packet packet, Channel channel){
        channel.writeAndFlush(packet, channel.voidPromise());
    }
}
