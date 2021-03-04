package lesson11.guava.io;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.graph.Traverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;

/**
 * IO 与 文件工具
 *
 * 源与汇
 *
 *  	  字节	           字符
 * 读	ByteSource	    CharSource
 * 写	ByteSink	    CharSink
 *
 * @author Webb Dong
 * @date 2021-03-04 10:09 PM
 */
public class SourceAndSinkExample {

    @SneakyThrows
    private static void charSourceExample() {
        System.out.println("----------------- charSourceExample ------------------");
        File sourceFile = new File(CharStreamsExample.class
                .getClassLoader().getResource("text.txt").getPath());
        CharSource fileSource = Files.asCharSource(sourceFile, Charsets.UTF_8);
        ImmutableList<String> lineList = fileSource.readLines();
        System.out.println("lineList = " + lineList);
        System.out.println("fileSource.read() = " + fileSource.read());
        System.out.println();

        // 将读取到得文件内容，按照空白字符分割成 HashMultiset
        HashMultiset<String> hashMultiset = HashMultiset.create(
                Splitter.on(CharMatcher.whitespace())
                        .trimResults()
                        .omitEmptyStrings()
                        .split(fileSource.read()));
        System.out.println("hashMultiset = " + hashMultiset);
        System.out.println();
    }

    @SneakyThrows
    private static void charSinkExample() {
        System.out.println("----------------- charSinkExample ------------------");
        File sourceFile = new File(CharStreamsExample.class
                .getClassLoader().getResource("text.txt").getPath());
        File sinkFile = new File(CharStreamsExample.class
                .getClassLoader().getResource("").getPath() + "text2.txt");
        Files.asCharSource(sourceFile, Charsets.UTF_8).copyTo(Files.asCharSink(sinkFile, Charsets.UTF_8));
        System.out.println(Files.asCharSource(sinkFile, Charsets.UTF_8).read());
        sinkFile.delete();
    }

    @SneakyThrows
    private static void byteSourceExample() {
        System.out.println("----------------- byteSourceExample ------------------");
        File sourceFile = new File(CharStreamsExample.class
                .getClassLoader().getResource("text.txt").getPath());
        HashCode hash = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        System.out.println(hash.toString());
    }

    @SneakyThrows
    private static void byteSinkExample() {
        System.out.println("----------------- byteSinkExample ------------------");
        URL resource = CharStreamsExample.class.getClassLoader().getResource("text.txt");
        File sinkFile = new File(CharStreamsExample.class
                .getClassLoader().getResource("").getPath() + "text2.txt");
        Resources.asByteSource(resource).copyTo(Files.asByteSink(sinkFile));
        Files.asByteSource(new File(resource.getPath())).copyTo(Files.asByteSink(sinkFile));
        System.out.println(Files.asCharSource(sinkFile, Charsets.UTF_8).read());
        sinkFile.delete();
    }

    private static void filesExample() {
        System.out.println("----------------- filesExample ------------------");
        // 获取文件扩展名
        System.out.println(Files.getFileExtension("text2.txt"));
        // 获取去除了扩展名的文件名
        System.out.println(Files.getNameWithoutExtension("text2.txt"));

        // 遍历指定目录的文件和文件夹
        Traverser<File> traverser = Files.fileTraverser();
        Iterable<File> iterable = traverser.breadthFirst(new File("/Users/dongwenbin/Develop"));
        iterable.forEach(f -> System.out.println(f.getName()));
    }

    public static void main(String[] args) {
        charSourceExample();
        charSinkExample();
        byteSourceExample();
        byteSinkExample();
        filesExample();
    }

}
