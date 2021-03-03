package lesson11.guava.strings;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;

/**
 * Joiner 连接器
 * @author Webb Dong
 * @date 2021-03-03 5:32 PM
 */
public class JoinerExample {

    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("Harry", null, "Ron", "Hermione", null);
        // JDK 的 String.join 不会忽略 null
        System.out.println(String.join("&", stringList));

        // 连接时忽略掉 null
        Joiner joiner1 = Joiner.on(";").skipNulls();
        String joinedStr = joiner1.join("Harry", null, "Ron", "Hermione", null);
        System.out.println(joinedStr);
        System.out.println(Joiner.on(",").join(Arrays.asList(1, 5, 7)));
    }

}
