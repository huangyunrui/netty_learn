package com.hyr.tomcat.bio;

public class FirstServlet extends GPServlet{
    @Override
    public void doPost(GPRequest request, GPResponse response) throws Exception {
        response.write("hello world post");
    }

    @Override
    public void doGet(GPRequest request,GPResponse response) throws Exception {
        response.write("hello world get");
    }
}
