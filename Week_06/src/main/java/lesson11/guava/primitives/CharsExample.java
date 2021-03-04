package lesson11.guava.primitives;

import com.google.common.primitives.Chars;

/**
 * @author Webb Dong
 * @date 2021-03-04 11:52
 */
public class CharsExample {

    public static void main(String[] args) {
        char[] chars = {'f', 'w', 'c', 'e', 'a', 'b'};
        System.out.println(Chars.contains(chars, 'c'));
    }

}
