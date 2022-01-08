package com.hyr.tomcat.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GPTomcat {
    private int port = 8080;
    private ServerSocket server;
    private Map<String, GPServlet> servletMap = new HashMap<>();

    private Properties webXml = new Properties();
    private void init(){
        String WEB_INF = this.getClass().getResource("/").getPath();
        try {
            FileInputStream fileInputStream = new FileInputStream(WEB_INF+"web.properties");
            webXml.load(fileInputStream);
            for (Object k : webXml.keySet()){
                String key = k.toString();
                if (key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$","");
                    String url = webXml.getProperty(key);
                    String className = webXml.getProperty(servletName+".className");

                    GPServlet  obj = (GPServlet) Class.forName(className).newInstance();
                    servletMap.put(url, obj);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    public void start(){
        init();

        try {
            server = new ServerSocket(this.port);
            System.out.println("GPTomcat is started listen port:"+this.port);

            while (true){
                Socket client = server.accept();
                process(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void process(Socket client) throws Exception {
        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();

        GPRequest gpRequest = new GPRequest(is);
        GPResponse gpResponse = new GPResponse(os);

        String url = gpRequest.getUrl();
        if (servletMap.containsKey(url)){
            servletMap.get(url).service(gpRequest, gpResponse);
        }else {
            gpResponse.write("404 - Not Found");
        }
        os.flush();
        os.close();

        is.close();
        client.close();
    }

    public static void main(String[] args) {
        new GPTomcat().start();
    }
}
