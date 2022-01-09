package com.hyr.tomcat.nio;



public abstract class GPNioServlet {
    public void service(GPNioRequest request, GPNioResponse response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else if ("POST".equalsIgnoreCase(request.getMethod())){
            doPost(request, response);
        }
    }

    public abstract void doPost(GPNioRequest request, GPNioResponse response) throws Exception ;


    public abstract void doGet(GPNioRequest request, GPNioResponse response) throws Exception;
}
