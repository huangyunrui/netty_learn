package com.hyr.tomcat.bio;

import java.io.IOException;
import java.io.InputStream;

public class GPRequest {

    private String method;
    private String url;
    public GPRequest(InputStream inputStream){
        String content = "";
        byte[] buff = new byte[1024];
        int len = 0;
        try {
            if ((len = inputStream.read(buff)) > 0){
                content = new String(buff,0,len);
            }
            if (len < 1){
                return;
            }
            String line = content.split("\\n")[0];
            String[] arr = line.split("\\s");
            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getMethod() {
        return method;
    }
    public String getUrl(){
        return url;
    }
}
