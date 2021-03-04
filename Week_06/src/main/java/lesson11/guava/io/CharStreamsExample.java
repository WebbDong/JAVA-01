package lesson11.guava.io;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 字符流
 * @author Webb Dong
 * @date 2021-03-04 6:33 PM
 */
public class CharStreamsExample {

    public static void main(String[] args) throws IOException {
        try (InputStream in = CharStreamsExample.class
                .getClassLoader().getResourceAsStream("text.txt")) {
            System.out.println(CharStreams.toString(new InputStreamReader(in)));
        }
        System.out.println();

        try (InputStream in = CharStreamsExample.class
                .getClassLoader().getResourceAsStream("text.txt")) {
            List<String> lineList = CharStreams.readLines(new InputStreamReader(in));
            lineList.forEach(line -> System.out.println(line));
        }
        System.out.println();

        try (InputStream in = CharStreamsExample.class
                .getClassLoader().getResourceAsStream("text.txt")) {
            InputStreamReader reader = new InputStreamReader(in);
            // 忽略5个字符
            CharStreams.skipFully(reader, 5);
            System.out.println(CharStreams.toString(reader));
        }
    }

}
