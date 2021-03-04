package lesson11.guava.primitives;

import com.google.common.primitives.Floats;

/**
 * 原生类型 float 工具类
 * @author Webb Dong
 * @date 2021-03-04 01:52
 */
public class FloatsExample {

    public static void main(String[] args) {
        // 如果是实数返回 true
        boolean isFinite1 = Floats.isFinite(50.25f);
        System.out.println(isFinite1);
        boolean isFinite2 = Floats.isFinite(8.0f / 6.0f);
        System.out.println(isFinite2);
    }

}
