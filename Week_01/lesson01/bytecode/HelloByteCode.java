package lesson01.bytecode;

/**
 * @author Webb Dong
 * @description: 字节码
 * @date 2021-01-07 23:22
 */
public class HelloByteCode {

    // 如果实例方法没有被调用使用，编译器将会直接去除它，在编译后的字节码文件中不会有这个方法
    public double myPublicMethod(int x, double d, String str) {
        System.out.println("myPublicMethod, x = " + x + ", d = " + d + ", str = " + str);
        return x + d;
    }

    public static void myPublicStaticMethod() {
        System.out.println("myPublicStaticMethod");
    }

    public static String myPublicStaticMethod2(int a, double b) {
        return "Hello";
    }

    // 私有方法编译器可能会将其进行内联
    private int myPrivateMethod(int x, int b) {
        System.out.println("myPrivateMethod, x = " + x + ", b = " + b);
        return x;
    }

    private static void myPrivateStaticMethod() {
        System.out.println("myPrivateStaticMethod");
    }

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

        int[] iArr = {1, 7, 220, 50, 1000};
        int elementValue = iArr[0];

        if (x == 10) {
            System.out.println("x = 10");
        } else if (x == 20) {
            System.out.println("x = 20");
        } else {
            System.out.println("x = " + x);
        }

        for (int i = 0, len = iArr.length; i < len; i++) {
            sum += iArr[i];
        }

        switch (y) {
            case 10:
                System.out.println("y = 10");
            case 50:
                System.out.println("y = 50");
            default:
        }

        HelloByteCode helloByteCode = new HelloByteCode();
        helloByteCode.myPublicMethod(80, 90, "Hello");
    }

}
