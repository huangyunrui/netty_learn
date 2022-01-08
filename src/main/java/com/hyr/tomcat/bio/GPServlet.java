package com.hyr.tomcat.bio;

public abstract class GPServlet {
    public void service(GPRequest request, GPResponse response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request);
        }else if ("POST".equalsIgnoreCase(request.getMethod())){
            doPost(request, response);
        }
    }

    public abstract void doPost(GPRequest request, GPResponse response) throws Exception ;


    public abstract void doGet(GPRequest request) throws Exception;
}