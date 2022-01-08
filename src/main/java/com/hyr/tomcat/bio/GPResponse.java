package com.hyr.tomcat.bio;

import java.io.IOException;
import java.io.OutputStream;

public class GPResponse {
    private OutputStream outputStream;
    public GPResponse(OutputStream outputStream){
        this.outputStream = outputStream;
    }

    public void write(String s) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\n");
        sb.append("Content-type: text/html;\n");
        sb.append("\r\n");
        sb.append(s);
        outputStream.write(sb.toString().getBytes());
    }
}
