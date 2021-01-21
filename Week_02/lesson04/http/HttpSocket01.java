package lesson04.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Webb Dong
 * @description: HttpSocket01
 * @date 2021-01-21 17:04
 */
public class HttpSocket01 {

    /**
     * 单线程模式、只能一个请求一个请求的处理，高并发场景性能很差。
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                try (final Socket socket = serverSocket.accept()) {
                    service(socket);
                }
            }
        }
    }

    private static void service(Socket socket) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio1";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
        }
    }

}
