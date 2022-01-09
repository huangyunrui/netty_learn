package com.hyr.rpc.provider;

import com.hyr.rpc.api.IRpcService;

public class IRpcServiceImpl implements IRpcService {

    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int sub(int a, int b) {
        return a-b;
    }

    @Override
    public int mult(int a, int b) {
        return a*b;
    }

    @Override
    public int div(int a, int b) {
        return a/b;
    }
}
