package com.hyr.rpc.provider;

import com.hyr.rpc.api.IRpcHelloService;

public class IRpcHelloServiceImpl implements IRpcHelloService {
    @Override
    public String hello(String hello) {
        return "Hello "+ hello +"!";
    }
}
