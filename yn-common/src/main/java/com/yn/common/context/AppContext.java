package com.yn.common.context;

import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collection;

/**
 * @author dhc
 * 2019-03-05 12:22
 */
public class AppContext {
    public static ConfigurableApplicationContext context;

    /**
     * 根据名称获取bean
     *
     * @param beanName Bean的名称
     * @return Bean
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * 根据bean名称获取指定类型bean
     *
     * @param beanName bean名称
     * @param clazz    返回的bean类型,若类型不匹配,将抛出异常
     * @param <T>      bean泛型
     * @return Bean
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }

    /**
     * 根据类型获取bean，有多项时，返回类名相同的项，否则返回第一项
     *
     * @param clazz bean类型
     * @param <T>   bean泛型
     * @return Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            Collection<T> beans = context.getBeansOfType(clazz).values();
            return beans.isEmpty() ? null : beans.stream().filter(t -> t.getClass().getName().equals(clazz.getName())).findFirst().orElse(beans.iterator().next());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否包含bean
     *
     * @param beanName bean名称
     * @return 是否包含
     */
    public static boolean containsBean(String beanName) {
        return context.containsBean(beanName);
    }

    /**
     * 是否是单例
     *
     * @param beanName bean名称
     * @return 是否单例
     */
    public static boolean isSingleton(String beanName) {
        return context.isSingleton(beanName);
    }

    /**
     * bean的类型
     *
     * @param beanName bean名称
     * @return Bean的类型
     */
    public static Class getType(String beanName) {
        return context.getType(beanName);
    }
}
