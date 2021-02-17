package lesson09.beanfactory.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Webb Dong
 * @description: School
 * @date 2021-02-15 23:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {

    private String name;

    private Integer foundingYears;

}
