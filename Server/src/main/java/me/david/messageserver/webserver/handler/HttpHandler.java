package me.david.messageserver.webserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HttpHandler extends SimpleChannelInboundHandler<Request> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        ByteBuf buf = Unpooled.buffer();
        System.out.println(request.getPath());
        switch (request.getPath().toLowerCase()){
            case "/threads/index.html":{
                for(Thread thread : Thread.getAllStackTraces().keySet()) {
                    buf.writeBytes((thread.getName() + "<br>\r\n").getBytes());
                }
            }
        }
        new Response(HttpResponseStatus.OK, buf).write(ctx);
    }
}
