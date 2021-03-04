package lesson11.guava.primitives;

import com.google.common.primitives.Doubles;

import java.util.List;

/**
 * @author Webb Dong
 * @date 2021-03-04 02:03
 */
public class DoublesExample {

    public static void main(String[] args) {
        List<Double> doubleList = Doubles.asList(89.22, 125.23, 456.44, 103.33);
        System.out.println(doubleList);
        System.out.println(Doubles.constrainToRange(20.45, 10.25, 30.20));
        System.out.println(Doubles.join(",", new double[] {24.31, 12.44, 13.45, 42.22, 45.55}));
    }

}
