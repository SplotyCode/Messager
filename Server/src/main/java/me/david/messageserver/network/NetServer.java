package me.david.messageserver.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.david.messagecore.netwok.Decoder;
import me.david.messagecore.netwok.Encoder;
import me.david.messagecore.netwok.Packet;
import me.david.messagecore.utils.ThreadUtil;
import me.david.messageserver.Server;

import java.util.Arrays;
import java.util.List;

/*
 * The Netty Network Server
 */
public class NetServer extends Thread {

    private int port, autoreconnectinterval;
    private boolean epoll, keepalive, autoreconnect;
    private final List<Class<? extends Packet>> OUT_PACKETS = Arrays.asList();
    private final List<Class<? extends Packet>> IN_PACKETS = Arrays.asList();


    private EventLoopGroup elg;

    public NetServer(int port, boolean epoll, boolean keepalive, boolean autoreconnect, int autoreconnectinterval){
        super("Main Netty NetServer");
        this.port = port;
        this.epoll = epoll;
        this.keepalive = keepalive;
        this.autoreconnect = autoreconnect;
        this.autoreconnectinterval = autoreconnectinterval;
    }

    @Override
    public void run() {
        Server.instance.logger.info("Starting NetServer on Port " + port + "!");
        elg = epoll ? new EpollEventLoopGroup(0, ThreadUtil.getThreadFactory("Netty NetServer Epoll Child #%d")) : new NioEventLoopGroup(0, ThreadUtil.getThreadFactory("Netty NetServer Epoll Child #%d"));
        try {
            new ServerBootstrap()
                    .group(elg)
                    .channel(epoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new Encoder().setOutpackets(OUT_PACKETS))
                                    .addLast(new Decoder().setInpackets(IN_PACKETS))
                                    .addLast(new ServerHandler());
                        };
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, keepalive)
                    .bind(port).sync().channel().closeFuture().syncUninterruptibly();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            elg.shutdownGracefully();
        }
        Server.instance.logger.info("NetServer down!" + (autoreconnect?" Reconnecting in " + autoreconnectinterval/1000 + "s":""));
        if(autoreconnect){
            try {
                Thread.sleep(autoreconnectinterval);
                Server.instance.logger.info("Restarting NetServer!");
                run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown(){
        elg.shutdownGracefully();
    }
}
