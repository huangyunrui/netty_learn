package com.hyr.tomcat.nio;



public class NioFirstServlet extends GPNioServlet {

    @Override
    public void doPost(GPNioRequest request, GPNioResponse response) throws Exception {
        System.out.println(request.getParameters());
        response.write("Post");
    }

    @Override
    public void doGet(GPNioRequest request, GPNioResponse response) throws Exception {
        System.out.println(request.getParameters());
        response.write("Get");
    }
}
