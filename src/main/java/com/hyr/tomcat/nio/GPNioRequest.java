package com.hyr.tomcat.nio;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class GPNioRequest {
    private ChannelHandlerContext context;
    private HttpRequest request;
    public GPNioRequest(ChannelHandlerContext ctx, HttpRequest request) {
        this.context = ctx;
        this.request = request;
    }

    public String getUrl(){
        String[] split = request.uri().split("\\?");
        return split[0];
    }

    public String getMethod() {
        return request.method().name();
    }

    public String getParameter(String name){
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if (null == param){
            return null;
        }else {
            return param.get(0);
        }
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        return queryStringDecoder.parameters();
    }
}
