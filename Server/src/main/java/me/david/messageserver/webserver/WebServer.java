package me.david.messageserver.webserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpServerCodec;
import me.david.messagecore.utils.ThreadUtil;
import me.david.messageserver.Server;
import me.david.messageserver.webserver.handler.HttpDecoder;
import me.david.messageserver.webserver.handler.HttpHandler;
import me.david.messageserver.webserver.handler.SocketSwitcher;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class WebServer extends Thread {

    private EventLoopGroup elg;
    private int autoreconnectinterval;
    private boolean epoll, autoreconnect;
    private SocketAddress address;

    public WebServer(SocketAddress address, boolean epoll, boolean autoreconnect, int autoreconnectinterval){
        super("Main Netty WebServer");
        this.address = address;
        this.epoll = epoll;
        this.autoreconnect = autoreconnect;
        this.autoreconnectinterval = autoreconnectinterval;
    }

    @Override
    public void run() {
        Server.instance.logger.info("Starting WebServer on Port " + ((InetSocketAddress)address).getPort() + "!");
        elg = epoll ? new EpollEventLoopGroup(0, ThreadUtil.getThreadFactory("Netty Webserver Epoll Child #%d")) : new NioEventLoopGroup(0, ThreadUtil.getThreadFactory("Netty Webserver Epoll Child #%d"));
        try {
            new ServerBootstrap()
                    .group(elg)
                    .channel(epoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline()
                                    .addLast("httpCodec", new HttpServerCodec())
                                    .addLast("webSocketSwitcher", new SocketSwitcher())
                                    .addLast("compressor", new HttpContentCompressor())
                                    .addLast("decoder", new HttpDecoder())
                                    .addLast("httpHandler", new HttpHandler());
                        };
                    })
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .localAddress(address).bind().sync().channel().closeFuture().syncUninterruptibly();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            elg.shutdownGracefully();
        }
        Server.instance.logger.info("WebServer down!" + (autoreconnect?" Reconnecting in " + autoreconnectinterval/1000 + "s":""));
        if(autoreconnect){
            try {
                Thread.sleep(autoreconnectinterval);
                Server.instance.logger.info("Restarting WebServer!");
                run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        elg.shutdownGracefully();
    }
}
