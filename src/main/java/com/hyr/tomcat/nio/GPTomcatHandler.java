package com.hyr.tomcat.nio;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;

public class GPTomcatHandler extends ChannelInboundHandlerAdapter {
    private Map<String, GPNioServlet> servletMap;

    public GPTomcatHandler(Map<String, GPNioServlet> servletMap) {
        this.servletMap  = servletMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest){
            System.out.println("hello");
            HttpRequest req = (HttpRequest) msg;
            GPNioRequest request = new GPNioRequest(ctx, req);
            GPNioResponse response = new GPNioResponse(ctx,req);

            String url = request.getUrl();
            if (servletMap.containsKey(url)){
                servletMap.get(url).service(request, response);
            }else {
                response.write("404 not found");
            }
        }
    }
}
