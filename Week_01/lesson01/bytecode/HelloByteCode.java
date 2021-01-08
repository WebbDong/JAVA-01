package lesson01.bytecode;

/**
 * @author Webb Dong
 * @description: 字节码
 * @date 2021-01-07 23:22
 */
public class HelloByteCode {

    public static void main(String[] args) {
        int x = 10;
        int y = 50;
        int sum = x + y + 60;
        int division = y / x;
        int multiplication = x * y;
        int sub = y - x;
        System.out.printf("sum = %d, division = %d, multiplication = %d, sub = %d%n",
                sum, division, multiplication, sub);

        byte b = 5;
        long l = 6000000L;
        float f = 3.14f;
        double d = 4125.5647;
        boolean bool = true;
        System.out.printf("b = %d, l = %d, f = %f, d = %f, bool = %b%n", b, l, f, d , bool);

        if (x == 10) {
            System.out.println("x = 10");
        }
        for (int i = 0; i < 50; i++) {
            sum += i;
        }
        switch (y) {
            case 10:
                System.out.println("y = 10");
            case 50:
                System.out.println("y = 50");
            default:
        }
    }

}
