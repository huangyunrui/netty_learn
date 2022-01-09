package com.hyr.rpc.comsumer;

import com.hyr.rpc.protocol.InvokerProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxy {
    public static <T>T create(Class<?> clazz){
        try {
            MethodProxy methodProxy = new MethodProxy(clazz);
            Class<?> [] interfaces = clazz.isInterface() ? new Class[]{clazz} : clazz.getInterfaces();

            T result = (T) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, methodProxy);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    private static class MethodProxy implements InvocationHandler {
        private Class<?> clazz;
        public MethodProxy(Class<?> clazz) {
            this.clazz = clazz;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Object.class.equals(method.getDeclaringClass())){
                try {
                    method.invoke(this, args);
                }catch (Throwable t){
                    t.printStackTrace();
                }
            }else {
                return rpcInvoke(proxy, method, args);
            }
            return null;
        }

        private Object rpcInvoke(Object proxy, Method method, Object[] args) {
            InvokerProtocol msg = new InvokerProtocol();
            msg.setClassName(this.clazz.getName());
            msg.setMethodName(method.getName());
            msg.setValues(args);
            msg.setParames(method.getParameterTypes());
            final RpcProxyHandler consumerHandler = new RpcProxyHandler();
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            try {
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                ChannelPipeline pipeline = socketChannel.pipeline();
                                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4,0,4));
                                pipeline.addLast(new LengthFieldPrepender(4));
                                pipeline.addLast(new ObjectEncoder());
                                pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                pipeline.addLast(consumerHandler);
                            }
                        });
                ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
                future.channel().writeAndFlush(msg).sync();
                future.channel().closeFuture().sync();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                group.shutdownGracefully();
            }

            return consumerHandler.getResponse();
        }
    }
}
