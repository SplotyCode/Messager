package me.david.messagecore.utils;

import io.netty.channel.Channel;
import me.david.messagecore.netwok.Packet;

public final class ChannelUtil {

    public static void sendPacket(Packet packet, Channel channel){
        channel.writeAndFlush(packet, channel.voidPromise());
    }
}
