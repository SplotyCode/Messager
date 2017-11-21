package me.david.messageserver.webserver.handler;

import com.google.gson.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import me.david.messageserver.webserver.WebServerError;

import java.util.ArrayList;
import java.util.List;

public class SocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    public static final List<SocketHandler> HANDLER = new ArrayList<SocketHandler>();
    private static final JsonParser JSON_PARSER = new JsonParser();

    private Channel channel;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.ServerHandshakeStateEvent) {
            this.channel = ctx.channel();
            HANDLER.add(this);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if(this.channel != null)
            this.channel.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if(frame instanceof TextWebSocketFrame) {
            try {
                JsonElement jsonElement = JSON_PARSER.parse(((TextWebSocketFrame) frame).text());

                JsonArray root;
                if(!jsonElement.isJsonArray() || (root = jsonElement.getAsJsonArray()).size() != 2) {
                    this.channel.write(new TextWebSocketFrame(WebServerError.INVALID_REQUEST.getError()), this.channel.voidPromise());
                    return;
                }

                JsonObject payload = root.get(1).isJsonObject() ? root.get(1).getAsJsonObject() : new JsonObject();
                switch (root.get(0).getAsString()) {
                    default:
                        this.channel.write(new TextWebSocketFrame(WebServerError.UNKNOWN_ACTION.getError()), this.channel.voidPromise());
                        break;
                }
            } catch (UnsupportedOperationException | JsonParseException e) {
                this.channel.write(new TextWebSocketFrame(WebServerError.INVALID_REQUEST.getError()), this.channel.voidPromise());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketHandler.HANDLER.remove(this);
    }
}
