package com.hyr.rpc.register;

import com.hyr.rpc.protocol.InvokerProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryHandler extends ChannelInboundHandlerAdapter {

    private ConcurrentHashMap<String, Object> registryMap;
    private List<String> classNames = new ArrayList<>();

    public RegistryHandler(ConcurrentHashMap<String, Object> registryMap){
        this.registryMap = registryMap;
        scannerClass("com.hyr.rpc.provider");
        doRegister();
    }

    private void doRegister() {
        if (classNames.size() == 0) return;

        for (String className : classNames){
            try {
                Class<?> aClass = Class.forName(className);
                Class<?> i = aClass.getInterfaces()[0];
                registryMap.put(i.getName(), aClass.newInstance());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void scannerClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()){
            if (file.isDirectory()){
                scannerClass(packageName+"."+file.getName());
            }else {
                classNames.add(packageName+"."+file.getName().replace(".class","").trim());
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InvokerProtocol request = (InvokerProtocol) msg;
        Object result = new Object();
        if (registryMap.containsKey(request.getClassName())){
            Object clazz = registryMap.get(request.getClassName());
            Method method = clazz.getClass().getMethod(request.getMethodName());
            result = method.invoke(clazz, request.getValues());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }
}
