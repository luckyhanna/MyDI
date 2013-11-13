package my.di.util;


import my.di.cache.CacheManager;
import my.di.cache.CacheMapManager;
import my.di.container.ApplicationContext;
import org.reflections.Reflections;
import my.di.annotations.*;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Aceasta clasa extinde java.util.reflection
 */
public class Scanner {


    private static CacheMapManager cacheMapManager = CacheMapManager.getInstance();

    private static CacheManager cacheManager = CacheManager.getInstance();

    /**
     * Aceasta metoda va scana clasa clazz dupa annotarile @Bean
     * @param - clazz reprezinta clasa dupa care se va scana
     * @return - Map<String,Bean> reprezinta toate bean-urile din aceasta clasa
     */
    /*public static Map<String,Bean> scanForBeans(final Class clazz) throws IllegalAccessException, InstantiationException {

        if (!clazz.isAnnotationPresent(Configuration.class)) {
            throw new RuntimeException("Class "+clazz+" is not an @Configuration class");
        }
        Map<String,Class> beanNames = cacheMapManager.getBeanNames();
        Map<Class,Object> singletons = cacheMapManager.getSingletons();

        Map<String,Bean> ret = new HashMap<String, Bean>();
        Object containingObject = clazz.newInstance();

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(my.di.annotations.Bean.class)) {
                my.di.annotations.Bean beanAnnotation = method.getAnnotation(my.di.annotations.Bean.class);

                String name = beanAnnotation.value() != null ? beanAnnotation.value() : method.getName();
                if (beanNames.get(name) != null) {
                    throw new RuntimeException("Multiple bean definitions for type "+name);
                }
                beanNames.put(name,method.getReturnType());
                singletons.put(method.getReturnType(),null);

                Map<String,Bean> dependencies = scanForDependencies(method);

                Bean.BeanBuilder builder = Bean.builder().withName(name)
                        .withType(method.getReturnType())
                        .withScope(Scope.SINGLETON)
                        .withMethod(method)
                        .withContainingClass(clazz)
                        .withContainingObject(containingObject)
                        .withDependencies(dependencies);
                Bean bean = builder.build();
                ret.put(bean.getName(), bean);
            }
        }
        return ret;
    }*/

    /**
     * Aceasta metoda va scana clasa clazz dupa annotarile @Bean
     * @param - clazz reprezinta clasa dupa care se va scana
     * @return - Map<String,Bean> reprezinta toate bean-urile din aceasta clasa
     */
    public static Map<String,Bean> scanBeans(final Class clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException {
        if (!clazz.isAnnotationPresent(Configuration.class)) {
            throw new RuntimeException("Class "+clazz+" is not an @Configuration class");
        }
        Map<String,Bean> beanMap = new HashMap<String, Bean>();
        Map<String,Bean> beanCache = cacheManager.getBeanCache();

        Object containingObject = clazz.newInstance();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(my.di.annotations.Bean.class)) {
                my.di.annotations.Bean beanAnnotation = method.getAnnotation(my.di.annotations.Bean.class);
                String name = beanAnnotation.value() != null ? beanAnnotation.value() : method.getName();
                if (beanCache.containsKey(name)) {
                    throw new RuntimeException("Multiple bean definition for type " + name);
                }
                Bean.BeanBuilder builder = Bean.builder().withName(name)
                        .withType(method.getReturnType())
                        .withScope(Scope.SINGLETON)
                        .withMethod(method)
                        .withContainingClass(clazz)
                        .withContainingObject(containingObject);
                if (method.isAnnotationPresent(Inject.class)) {
                    builder.withDependencies(scanDependencies(method));
                } else {
                    builder.withDependencies(null);
                }
                Bean bean = builder.build();
                beanMap.put(name,bean);
                beanCache.put(name,bean);
            }
        }

        return beanMap;
    }


    /**
     * Aceasta metoda va scana dupa annotarile @Inject si @Qualifier
     * @param method - reprezinta metoda dupa care se va scana
     * @return - Map<String,Bean> reprezinta toate dependintele metodei method
     */
    /*public static Map<String,Bean> scanForDependencies(Method method) {
        Map<String,Bean> ret = new HashMap<String, Bean>();

        if (method.isAnnotationPresent(Inject.class)) {
            Class[] params = method.getParameterTypes();
            Annotation[][] annotations = method.getParameterAnnotations();

            Method[] candidates = method.getDeclaringClass().getDeclaredMethods();

            for (int i = 0; i < annotations.length; i++) {
                if (annotations[i].length > 0) {
                    if (annotations[i][0].annotationType().equals(Qualifier.class)) {
                        String annotationValue = ((Qualifier)annotations[i][0]).value();
                        System.out.println(annotationValue + " - " + params[i]);
                        for (Method candidate : candidates) {
                            if (candidate.isAnnotationPresent(my.di.annotations.Bean.class)) {
                                my.di.annotations.Bean beanAnnotation = candidate.getAnnotation(my.di.annotations.Bean.class);
                                if (beanAnnotation.value().equals(annotationValue)) {
//                                    ret.put(beanAnnotation.value() != null ? beanAnnotation.value() : method.getName(),);

                                }
                            }

                        }
                    }
                }
            }
        }

        return ret;
    }*/

    /**
     * Aceasta metoda va scana dupa annotarile @Inject si @Qualifier
     * @param method - reprezinta metoda dupa care se va scana
     * @return - Map<String,Bean> reprezinta toate dependintele metodei method
     */
    public static Map<String,Bean> scanDependencies(Method method) throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        Map<String,Bean> dependencyMap = new HashMap<String, Bean>();

        Map<String,Bean> beanCache = cacheManager.getBeanCache();
        Class[] params = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].length > 0) {
                String name;
                Method methodToInject;
                if (annotations[i][0].annotationType().equals(Qualifier.class)) {
                    name = ((Qualifier)annotations[i][0]).value();
                    methodToInject = getMethodByClassAndBeanValue(method.getDeclaringClass(),name);
                    if (methodToInject == null) {
                        throw new RuntimeException("No bean with value "+ name +" found for Inject");
                    }
                } else {
                    throw new RuntimeException("What annotation is this???");
                    /*methodToInject = getMethodByClassAndReturnType(method.getDeclaringClass(), params[i]);
                    if (methodToInject == null) {
                        throw new RuntimeException("No bean of type "+ params[i] +" found for Inject");
                    }
                    name = methodToInject.getName();*/
                }
                if (beanCache.containsKey(name)) {
                    dependencyMap.put(name,beanCache.get(name));
                } else {
                    Class clazz = method.getDeclaringClass();
                    Object containingObject = clazz.newInstance();
                    Bean.BeanBuilder builder = Bean.builder().withName(name)
                            .withType(methodToInject.getReturnType())
                            .withScope(Scope.SINGLETON)
                            .withMethod(methodToInject)
                            .withContainingClass(clazz)
                            .withContainingObject(containingObject);

                    if (methodToInject.isAnnotationPresent(Inject.class)) {
                        builder.withDependencies(scanDependencies(methodToInject));
                    } else {
                         builder.withDependencies(null);
                    }
                    Bean bean = builder.build();                    
                    dependencyMap.put(name,bean);
                }
            } else {
                Method methodToInject = getMethodByClassAndReturnType(method.getDeclaringClass(), params[i]);
                if (methodToInject == null) {
                    throw new RuntimeException("No bean of type "+ params[i] +" found for Inject");
                }
                String name = methodToInject.getName();
                if (beanCache.containsKey(name)) {
                    dependencyMap.put(name,beanCache.get(name));
                } else {
                    Class clazz = method.getDeclaringClass();
                    Object containingObject = clazz.newInstance();
                    Bean.BeanBuilder builder = Bean.builder().withName(name)
                            .withType(methodToInject.getReturnType())
                            .withScope(Scope.SINGLETON)
                            .withMethod(methodToInject)
                            .withContainingClass(clazz)
                            .withContainingObject(containingObject);

                    if (methodToInject.isAnnotationPresent(Inject.class)) {
                        builder.withDependencies(scanDependencies(methodToInject));
                    } else {
                        builder.withDependencies(null);
                    }
                    Bean bean = builder.build();                    
                    dependencyMap.put(name,bean);
                }
            }
        }

        return dependencyMap;
    }

    private static Method getMethodByClassAndBeanValue(Class<?> declaringClass, String value) {
        Method[] methods = declaringClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(my.di.annotations.Bean.class)
                    && value.equals(method.getAnnotation(my.di.annotations.Bean.class).value())) {
                return method;
            }
        }
        return null;
    }

    private static Method getMethodByClassAndReturnType(Class<?> declaringClass, Class returnType) {
        Method[] methods = declaringClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getReturnType().equals(returnType)) {
                return method;
            }
        }
        return null;
    }

}
