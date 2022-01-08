package com.hyr.tomcat.bio;

public class FirstServlet extends GPServlet{
    @Override
    public void doPost(GPRequest request, GPResponse response) throws Exception {
        System.out.println("hello world post");
    }

    @Override
    public void doGet(GPRequest request) throws Exception {
        System.out.println("hello world get");
    }
}
