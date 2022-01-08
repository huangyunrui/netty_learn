package com.hyr.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class AIOClient {
    private final AsynchronousSocketChannel client;
    public AIOClient() throws IOException {
        client = AsynchronousSocketChannel.open();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new AIOClient().connect("127.0.0.1", 8080);

    }

    public void connect(String host, int port) throws InterruptedException {
        client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void unused, Void unused2) {
                try {
                    client.write(ByteBuffer.wrap("哈哈哈".getBytes())).get();
                    System.out.println("data send");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable throwable, Void unused) {
                throwable.printStackTrace();
            }
        });

        final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        client.read(byteBuffer, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer integer, Object o) {

                System.out.println("IO done :"+integer);
                System.out.println("IO result :"+new String(byteBuffer.array()));
            }

            @Override
            public void failed(Throwable throwable, Object o) {
                throwable.printStackTrace();
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }
}
