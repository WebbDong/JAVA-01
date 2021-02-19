package lesson09.xmlbeanfactory.bean;

import lombok.Data;

/**
 * @author Webb Dong
 * @description: TODO
 * @date 2021-02-19 22:49
 */
@Data
public class Klass {

    private String name;

    public void testMethod() {
        System.out.println(name);
    }

}
