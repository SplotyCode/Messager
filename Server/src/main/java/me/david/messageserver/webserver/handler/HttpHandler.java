package me.david.messageserver.webserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Map;

public class HttpHandler extends SimpleChannelInboundHandler<Request> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        ByteBuf buf = Unpooled.buffer();
        switch (request.getPath().toLowerCase()){
            case "/threads/index.html":{
                for(Map.Entry<Thread, StackTraceElement[]> en : Thread.getAllStackTraces().entrySet()) {
                    buf.writeBytes((en.getKey().getName() + " [" + en.getKey().getState().name() + "] Trace: " + en.getValue().length + "<br>\r\n").getBytes());
                    for(StackTraceElement element : en.getValue())
                        buf.writeBytes(("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + element.getClassName() + "#" + element.getMethodName() + ":" + element.getLineNumber() + "<br>\r\n").getBytes());
                }
            }
        }
        new Response(HttpResponseStatus.OK, buf).write(ctx);
    }
}
