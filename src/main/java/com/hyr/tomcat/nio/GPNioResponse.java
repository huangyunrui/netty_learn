package com.hyr.tomcat.nio;

import com.hyr.tomcat.bio.GPRequest;
import com.hyr.tomcat.bio.GPServlet;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class GPNioResponse {
    private ChannelHandlerContext context;
    private HttpRequest request;
    public GPNioResponse(ChannelHandlerContext ctx, HttpRequest request) {
        this.context = ctx;
        this.request = request;
    }

    public void write(String out) {
        try {
            if (out == null || out.length() == 0){
                return;
            }
            FullHttpResponse  httpResponse = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(out.getBytes(StandardCharsets.UTF_8)));
            httpResponse.headers().set("Content-Type","text/html");
            context.write(httpResponse);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            context.flush();
            context.close();
        }
    }
}
