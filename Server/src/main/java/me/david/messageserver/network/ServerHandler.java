package me.david.messageserver.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import me.david.messagecore.netwok.Packet;

public class ServerHandler extends SimpleChannelInboundHandler<Packet> {

    /*
     * Channles that are Authenticatet
     */
    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet inpacket) throws Exception {
        Channel channel = ctx.channel();
        if(!channels.contains(channel)) {

        }
    }


}
