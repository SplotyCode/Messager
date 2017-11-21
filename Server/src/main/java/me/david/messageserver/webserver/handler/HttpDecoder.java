package me.david.messageserver.webserver.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.multipart.*;

import java.io.File;
import java.util.List;

public class HttpDecoder extends MessageToMessageDecoder<Object> {

    private static final HttpDataFactory HTTP_DATA_FACTORY = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
    private static final FullHttpResponse INVALID_METHOD = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.METHOD_NOT_ALLOWED, Unpooled.EMPTY_BUFFER);

    private Request request;
    private HttpPostRequestDecoder decoder;
    private Channel channel;

    static {
        File baseDir = new File("Temp/");
        if(!baseDir.exists())
            baseDir.mkdirs();
        DiskFileUpload.deleteOnExitTemporaryFile = true;
        DiskFileUpload.baseDirectory = baseDir.getAbsolutePath();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        channel = ctx.channel();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Object obj, List<Object> list) throws Exception {
        if (obj instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) obj;

            QueryStringDecoder queryDecoder = new QueryStringDecoder(request.uri());
            this.request = new Request(request, queryDecoder.path());

            if (request.method().equals(HttpMethod.GET)) {
                this.request.getGet().putAll(queryDecoder.parameters());
            } else if (request.method().equals(HttpMethod.POST)) {
                try {
                    decoder = new HttpPostRequestDecoder(HTTP_DATA_FACTORY, request);
                } catch (HttpPostRequestDecoder.ErrorDataDecoderException e) {
                    channel.close();
                    return;
                }
            } else {
                channel.writeAndFlush(INVALID_METHOD.duplicate()).addListener(listener -> channel.close());
                return;
            }

            if (request.headers().contains(HttpHeaderNames.COOKIE))
                this.request.getCookies().addAll(ServerCookieDecoder.STRICT.decode(request.headers().get(HttpHeaderNames.COOKIE)));
        } else if (obj instanceof HttpContent) {
            HttpContent content = (HttpContent) obj;

            if (decoder != null) {
                try {
                    decoder.offer(content);
                } catch (HttpPostRequestDecoder.ErrorDataDecoderException e) {
                    channel.close();
                    return;
                }
            }

            if (content instanceof LastHttpContent) {
                if (decoder != null) {
                    try {
                        while (decoder.hasNext()) {
                            InterfaceHttpData data = decoder.next();
                            if (data != null)
                                switch (data.getHttpDataType()) {
                                    case Attribute:
                                        Attribute attribute = (Attribute) data;
                                        request.getPost().put(data.getName(), attribute.getValue());
                                        attribute.delete();
                                        break;

                                    case FileUpload:
                                        FileUpload fileUpload = (FileUpload) data;
                                        request.getPost().put(data.getName(), fileUpload.copy());
                                        break;

                                    default:
                                        data.release();
                                        break;
                                }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    decoder.destroy();
                    decoder = null;
                }

                if (request != null) {
                    list.add(request);
                    request = null;
                }
            }
        }
    }
}
