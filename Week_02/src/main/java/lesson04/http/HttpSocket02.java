package lesson04.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Webb Dong
 * @description: HttpSocket02
 * @date 2021-01-21 17:36
 */
public class HttpSocket02 {

    /**
     * 每个连接请求创建一个新线程来处理，并发吞吐量提高了，但是线程属于重量级资源，每次都创建一个线程比较耗时，而且如果
     * 并发量很大的话，会创建大量的线程。
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("new request");
                new Thread(() -> {
                    try {
                        service(socket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
    }

    private static void service(Socket socket) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio2";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
        } finally {
            socket.close();
        }
    }

}
