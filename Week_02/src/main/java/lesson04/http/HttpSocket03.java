package lesson04.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Webb Dong
 * @description: HttpSocket03
 * @date 2021-01-21 19:13
 */
public class HttpSocket03 {

    private static ExecutorService executorService = new ThreadPoolExecutor(
            80, 100, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(20), new ThreadPoolExecutor.AbortPolicy());

    /**
     * 使用线程池可以避免频繁创建线程的开销，但是依然基于 BIO 系统资源的利用率还是不高，性能提升不明显。
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8082)) {
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("new request");
                executorService.execute(() -> service(socket));
            }
        }
    }

    private static void service(Socket socket) {
        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio3";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
