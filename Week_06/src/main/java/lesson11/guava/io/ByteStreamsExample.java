package lesson11.guava.io;

import com.google.common.io.ByteStreams;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.InputStream;

/**
 * 字节流
 * @author Webb Dong
 * @date 2021-03-04 6:32 PM
 */
public class ByteStreamsExample {

    public static void main(String[] args) throws Exception {
        try (InputStream in = ByteStreamsExample.class
                .getClassLoader().getResourceAsStream("text.txt")) {
            byte[] bytes = ByteStreams.toByteArray(in);
            System.out.println(new String(bytes));
        }
        System.out.println();

        try (InputStream in = ByteStreamsExample.class
                .getClassLoader().getResourceAsStream("text.txt")) {
            ByteOutputStream out = new ByteOutputStream();
            ByteStreams.copy(in, out);
            System.out.println(new String(out.getBytes()));
        }
        System.out.println();

        try (InputStream in = ByteStreamsExample.class
                .getClassLoader().getResourceAsStream("text.txt")) {
            long exhaust = ByteStreams.exhaust(in);
            System.out.println("exhaust = " + exhaust);
        }
        System.out.println();

        try (InputStream in = ByteStreamsExample.class
                .getClassLoader().getResourceAsStream("text.txt")) {
            // 读取指定的字节数
            try (InputStream limit = ByteStreams.limit(in, 10)) {
                byte[] bytes = ByteStreams.toByteArray(limit);
                System.out.println(new String(bytes));
            }
        }
        System.out.println();

        try (InputStream in = ByteStreamsExample.class
                .getClassLoader().getResourceAsStream("text.txt")) {
            byte[] bytes = new byte[in.available()];
            ByteStreams.readFully(in, bytes);
            System.out.println(new String(bytes));
        }
    }

}
