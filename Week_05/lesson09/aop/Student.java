package lesson09.aop;

import lombok.Data;

/**
 * @author Webb Dong
 * @description: Student
 * @date 2021-02-14 16:47
 */
@Data
public class Student implements Person {

    @Override
    public void sayHello() {
        System.out.println("call sayHello()");
    }

}
