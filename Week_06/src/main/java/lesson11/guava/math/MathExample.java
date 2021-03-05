package lesson11.guava.math;

import com.google.common.math.BigIntegerMath;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;

import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * 数学运算
 * @author Webb Dong
 * @date 2021-03-05 2:25 PM
 */
public class MathExample {

    public static void main(String[] args) {
        int logFloor = LongMath.log2(10, RoundingMode.FLOOR);
        int mustNotOverflow = IntMath.checkedMultiply(5, 6);
        long quotient = LongMath.divide(10, 2, RoundingMode.UNNECESSARY);
        BigInteger nearestInteger = DoubleMath.roundToBigInteger(30.45, RoundingMode.HALF_EVEN);
        BigInteger sideLength = BigIntegerMath.sqrt(BigInteger.valueOf(40), RoundingMode.CEILING);
        System.out.println(logFloor);
        System.out.println(mustNotOverflow);
        System.out.println(quotient);
        System.out.println(nearestInteger);
        System.out.println(sideLength);
    }

}
