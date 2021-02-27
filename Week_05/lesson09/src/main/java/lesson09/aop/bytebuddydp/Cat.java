package lesson09.aop.bytebuddydp;

/**
 * @author Webb Dong
 * @description:
 * @date 2021-02-20 02:21
 */
public class Cat {

    public int eat() {
        System.out.println("invoke target method Cat.eat()");
        return 100;
    }

}
