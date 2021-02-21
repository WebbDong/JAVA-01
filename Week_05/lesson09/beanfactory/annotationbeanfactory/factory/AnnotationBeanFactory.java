package lesson09.beanfactory.annotationbeanfactory.factory;

import lesson09.beanfactory.annotationbeanfactory.annotation.aop.After;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.AfterReturning;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.AfterThrowing;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.Around;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.Aspect;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.Before;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.Pointcut;
import lesson09.beanfactory.annotationbeanfactory.annotation.context.Component;
import lesson09.beanfactory.annotationbeanfactory.annotation.context.Scope;
import lesson09.beanfactory.base.AbstractBeanFactory;
import lesson09.beanfactory.base.beanfactory.config.AopAdviceDefinition;
import lesson09.beanfactory.base.beanfactory.config.AopAspectDefinition;
import lesson09.beanfactory.base.beanfactory.config.AopPointcutDefinition;
import lesson09.beanfactory.base.beanfactory.config.BeanDefinition;
import lesson09.beanfactory.base.beanfactory.config.enums.AopAdviceTypeEnum;
import lesson09.beanfactory.base.beanfactory.config.enums.BeanScopeTypeEnum;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Webb Dong
 * @description: 注解 Bean 工厂
 * @date 2021-02-21 14:47
 */
public class AnnotationBeanFactory extends AbstractBeanFactory {

    private final String CLASSES_PATH;

    /**
     * 构造器
     * @param primarySource 启动类
     */
    public AnnotationBeanFactory(Class<?> primarySource) {
        File basePath = new File(Objects.requireNonNull(
                AnnotationBeanFactory.class.getClassLoader().getResource("")).getPath());
        File basePackagePath = new File(basePath, primarySource.getPackage()
                .getName().replace(".", File.separator));
        CLASSES_PATH = basePath.getAbsolutePath() + File.separator;
        scanPackages(basePackagePath);
    }

    /*
     * 递归扫描包
     * @param basePackagePath 根包路径

    private void scanPackages(File basePackagePath) {
        Arrays.stream(basePackagePath.listFiles()).forEach(f -> {
            if (f.isDirectory()) {
                scanPackages(f);
            } else {
                handleClassFile(f);
            }
        });
    }
     */

    /**
     * 非递归扫描包
     * @param basePackagePath 根包路径
     */
    private void scanPackages(File basePackagePath) {
        Deque<File> dirDeque = new LinkedList<>();
        File[] files = basePackagePath.listFiles();
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("basePackagePath has no files");
        }
        while (true) {
            for (File f : files) {
                if (f.isDirectory()) {
                    dirDeque.add(f);
                } else {
                    handleClassFile(f);
                }
            }
            File dir = dirDeque.poll();
            if (dir == null) {
                break;
            }
            files = dir.listFiles();
        }

        initAllSingletonBeansAspectBeans();
        initAllSingletonBeansAopProxyBeans();
    }

    /**
     * 初始化所有单例 bean 对应的切面 bean
     */
    private void initAllSingletonBeansAspectBeans() {
        aspectDefMap.values().stream()
                .filter(a -> {
                    BeanDefinition beanDef = beanDefCaching.get(a.getRef());
                    if (beanDef == null) {
                        throw new IllegalStateException("beanDef is null");
                    }
                    return beanDef.getScopeType() == BeanScopeTypeEnum.SINGLETON;
                })
                .forEach(a -> {
                    BeanDefinition beanDef = beanDefCaching.get(a.getRef());
                    try {
                        beanCaching.put(beanDef.getId(), Class.forName(beanDef.getClassName()).newInstance());
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * 处理字节码文件
     * @param classFile 字节码文件
     */
    @SneakyThrows
    private void handleClassFile(File classFile) {
        String className = classFile.getAbsolutePath().replace(CLASSES_PATH, "")
                .replace(File.separator, ".").replace(".class", "");
        Class<?> aClass = Class.forName(className);
        if (aClass.isAnnotation()) {
            return;
        }
        Arrays.stream(aClass.getAnnotations())
                .filter(a -> !a.getClass().isAnnotation())
                .forEach(a -> handleClassAnnotation(aClass, a));
    }

    /**
     * 处理类上的注解
     * @param aClass 当前类的字节码
     * @param annotation 当前类上的注解
     */
    private void handleClassAnnotation(Class<?> aClass, Annotation annotation) {
        if (annotation instanceof Component) {
            handleClassComponentAnnotation(aClass, (Component) annotation);
        } else if (annotation instanceof Aspect) {
            handleClassAspectAnnotation(aClass, (Aspect) annotation);
        }
    }

    /**
     * 处理类上的 Component 注解
     * @param aClass 当前类的字节码
     * @param component 当前类上的 Component 注解实例
     */
    @SneakyThrows
    private void handleClassComponentAnnotation(Class<?> aClass, Component component) {
        String beanId = component.value();
        if ("".equals(beanId)) {
            beanId = lowercaseFirstLetterOfClassName(aClass.getName());
        }

        Scope scope = aClass.getAnnotation(Scope.class);
        putBeanDefCachingByScopeAnnotation(scope, beanId, aClass.getName());

        if (scope != null && scope.value() == BeanScopeTypeEnum.PROTOTYPE) {
            return;
        }
        // 如果是单例模式，创建 bean 的实例，并且缓存
        beanCaching.put(beanId, aClass.newInstance());
    }

    /**
     * 处理类上的 Aspect 注解
     * @param aClass 当前类的字节码
     * @param aspect 当前类上的 Aspect 注解实例
     */
    private void handleClassAspectAnnotation(Class<?> aClass, Aspect aspect) {
        Method[] declaredMethods = aClass.getDeclaredMethods();
        if (declaredMethods.length == 0) {
            return;
        }

        Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap = new HashMap<>();
        String targetRef = null;
        for (Method m : declaredMethods) {
            if (m.isAnnotationPresent(Pointcut.class)) {
                Pointcut pointcut = m.getAnnotation(Pointcut.class);
                pointcutDefMap.put(pointcut.id(), AopPointcutDefinition.builder()
                        .id(pointcut.id())
                        .ref(pointcut.ref())
                        .build());
                targetRef = pointcut.ref();
            } else if (isAdviceAnnotation(m)) {
                initAdviceDefMap(adviceDefMap, m.getName(), getAopAdviceTypeEnumByAdviceAnnotation(m));
            }
        }

        if (targetRef == null) {
            throw new IllegalStateException("targetRef can not be null");
        }

        String ref = "".equals(aspect.ref()) ? lowercaseFirstLetterOfClassName(aClass.getName()) : aspect.ref();
        AopAspectDefinition aspectDef = AopAspectDefinition.builder()
                .id(ref)
                .ref(ref)
                .targetRef(targetRef)
                .adviceDefMap(adviceDefMap)
                .build();
        aspectDefMap.put(targetRef, aspectDef);

        Scope scope = aClass.getAnnotation(Scope.class);
        putBeanDefCachingByScopeAnnotation(scope, aspectDef.getId(), aClass.getName());
    }

    /**
     * 根据方法上的通知注解获取对应的通知类型枚举
     * @param m 方法
     * @return 返回对应的通知类型枚举
     */
    private AopAdviceTypeEnum getAopAdviceTypeEnumByAdviceAnnotation(Method m) {
        if (m.isAnnotationPresent(Around.class)) {
            return AopAdviceTypeEnum.AROUND;
        } else if (m.isAnnotationPresent(Before.class)) {
            return AopAdviceTypeEnum.BEFORE;
        } else if (m.isAnnotationPresent(After.class)) {
            return AopAdviceTypeEnum.AFTER;
        } else if (m.isAnnotationPresent(AfterReturning.class)) {
            return AopAdviceTypeEnum.AFTER_RETURNING;
        } else {
            return AopAdviceTypeEnum.AFTER_THROWING;
        }
    }

    /**
     * 判断此方法是否有通知注解
     * @param m 方法
     * @return 存在通知注解返回 true，否则返回 false
     */
    private boolean isAdviceAnnotation(Method m) {
        return m.isAnnotationPresent(Around.class)
                || m.isAnnotationPresent(Before.class)
                || m.isAnnotationPresent(After.class)
                || m.isAnnotationPresent(AfterReturning.class)
                || m.isAnnotationPresent(AfterThrowing.class);
    }

    /**
     * 类名首字母小写
     * @param className 全类名
     * @return 返回首字母小写的类名
     */
    private String lowercaseFirstLetterOfClassName(String className) {
        className = className.substring(className.lastIndexOf(".") + 1);
        return Character.toLowerCase(className.charAt(0)) + className.substring(1);
    }

    /**
     * 保存 bean 定义到 beanDefCaching 中
     * @param scope Scope 注解
     * @param beanId bean id
     * @param className 全类名
     */
    private void putBeanDefCachingByScopeAnnotation(Scope scope, String beanId, String className) {
        beanDefCaching.put(beanId, BeanDefinition.builder()
                .id(beanId)
                .name(beanId)
                .className(className)
                .scopeType(scope != null ? scope.value() : BeanScopeTypeEnum.SINGLETON)
                .build());
    }

}
