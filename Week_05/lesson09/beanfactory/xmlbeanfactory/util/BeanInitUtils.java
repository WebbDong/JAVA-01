package lesson09.beanfactory.xmlbeanfactory.util;

/**
 * @author Webb Dong
 * @description: Bean 初始化工具
 * @date 2021-02-17 19:05
 */
public class BeanInitUtils {

    public static Object getRealTypeValue(Class<?> paramType, String value) {
        if (Integer.class.equals(paramType)) {
            return Integer.parseInt(value);
        } else if (Long.class.equals(paramType)) {
            return Long.parseLong(value);
        } else if (Float.class.equals(paramType)) {
            return Float.parseFloat(value);
        } else if (Double.class.equals(paramType)) {
            return Double.parseDouble(value);
        } else if (Boolean.class.equals(paramType)) {
            return Boolean.valueOf(value);
        } else if (Byte.class.equals(paramType)) {
            return Byte.valueOf(value);
        } else if (Short.class.equals(paramType)) {
            return Short.valueOf(value);
        } else {
            return value;
        }
    }

    private BeanInitUtils() {}

}
