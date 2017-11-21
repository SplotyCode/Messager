package me.david.messageserver.network;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.packets.*;
import me.david.messagecore.utils.ChannelUtil;
import me.david.messageserver.user.Auth;

public class ServerHandler extends SimpleChannelInboundHandler<Packet> {

    /*
     * Channels that are Authenticated
     */
    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet inpacket) throws Exception {
        Channel channel = ctx.channel();
        if(!channels.contains(channel)) {
            if (inpacket instanceof AuthPacket){
                if (Auth.handleAuth((AuthPacket) inpacket, channel)) channels.add(channel);
            }else if(inpacket instanceof LoginPacket) {
                if (Auth.handleLogin((LoginPacket) inpacket, channel)) channels.add(channel);
            }else ChannelUtil.sendPacket(new NotAuthenticationRespone(), channel);
        }else{

        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if(channels.contains(ctx.channel())) channels.remove(ctx.channel());
    }
}
