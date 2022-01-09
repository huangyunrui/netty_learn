package com.hyr.rpc.comsumer;

import com.hyr.rpc.api.IRpcHelloService;
import com.hyr.rpc.api.IRpcService;

public class RpcConSumer {
    public static void main(String[] args) {
        IRpcHelloService rpcHelloService = RpcProxy.create(IRpcHelloService.class);
        System.out.println(rpcHelloService.hello("jack"));


        IRpcService iRpcService = RpcProxy.create(IRpcService.class);
        System.out.println(iRpcService.add(1,2));
        System.out.println(iRpcService.sub(1,2));
        System.out.println(iRpcService.mult(1,2));
        System.out.println(iRpcService.div(1,2));
    }
}
