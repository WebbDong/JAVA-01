package lesson11.guava.basicutilities;

import com.google.common.base.Objects;

/**
 * @author Webb Dong
 * @date 2021-02-28 8:17 PM
 */
public class ObjectExample {

    public static void main(String[] args) {
        System.out.println("Objects.equal(10, 20) = " + Objects.equal(10, 20));
        System.out.println("Objects.hashCode(10, \"str\") = " + Objects.hashCode(10, "str"));
    }

}
