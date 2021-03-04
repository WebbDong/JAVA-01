package lesson11.guava.primitives;

import com.google.common.primitives.Booleans;

import java.util.Arrays;

/**
 * @author Webb Dong
 * @date 2021-03-04 12:04
 */
public class BooleansExample {

    public static void main(String[] args) {
        boolean[] booleans1 = {true, false, false, true, true, true};
        System.out.println(Booleans.countTrue(booleans1));
        System.out.println(Booleans.join("|", booleans1));

        System.out.println(Booleans.trueFirst().compare(true, false));

        Booleans.reverse(booleans1);
        System.out.println(Arrays.toString(booleans1));
    }

}
