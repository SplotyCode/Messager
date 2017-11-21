package me.david.messageserver.webserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Response extends DefaultFullHttpResponse {

    private static final String JAVA_VERSION = new StringBuilder("Java/").append(Runtime.class.getPackage().getImplementationVersion()).toString();
    private static final String SERVER_VERSION = new StringBuilder("TachyonizedWebServer/1.2.42 ").append("Netty/4.1.0 ").append(JAVA_VERSION).toString();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private boolean close = false;

    public Response(HttpResponseStatus status, ByteBuf content) {
        super(HttpVersion.HTTP_1_1, status, content);

        this.headers()
                .set(HttpHeaderNames.DATE, DATE_FORMAT.format(Calendar.getInstance().getTime()))
                .set(HttpHeaderNames.CONTENT_LENGTH, content().readableBytes())
                .set(HttpHeaderNames.SERVER, SERVER_VERSION)
                .set("X-Powered-By", JAVA_VERSION);
    }

    public void write(ChannelOutboundInvoker channelOutboundInvoker) {
        this.headers().set(HttpHeaderNames.CONNECTION, this.close ? "Close" : "Keep-Alive");
        if(!this.close) {
            this.headers().set("KeepAlive", "timeout=5, max=99");
            channelOutboundInvoker.write(this, channelOutboundInvoker.voidPromise());
        } else
            channelOutboundInvoker.write(this).addListener(ChannelFutureListener.CLOSE);
    }
}
