package my.di.util;


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


    private CacheMapManager cacheMapManager = CacheMapManager.getInstance();

    /**
     * Aceasta metoda va scana clasa clazz dupa annotarile @Bean
     * @param - clazz reprezinta clasa dupa care se va scana
     * @return - Map<String,Bean> reprezinta toate bean-urile din aceasta clasa
     */
    public static Map<String,Bean> scanForBeans(final Class clazz) throws IllegalAccessException, InstantiationException {

        if (!clazz.isAnnotationPresent(Configuration.class)) {
            throw new RuntimeException("Class "+clazz+" is not an @Configuration class");
        }

        Map<String,Bean> ret = new HashMap<String, Bean>();

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(my.di.annotations.Bean.class)) {
                my.di.annotations.Bean beanAnnotation = method.getAnnotation(my.di.annotations.Bean.class);
                Map<String,Bean> dependencies = scanForDependencies(method);
                Bean.BeanBuilder builder = Bean.builder().withName(beanAnnotation.value() != null ? beanAnnotation.value() : method.getName())
                        .withType(method.getReturnType())
                        .withScope(Scope.SINGLETON)
                        .withMethod(method)
                        .withContainingClass(clazz)
                        .withContainingObject(clazz.newInstance())
                        .withDependencies(dependencies);
                Bean bean = builder.build();
                ret.put(bean.getName(), bean);
            }
        }

        return ret;

    }

    /**
     * Aceasta metoda va scana dupa annotarile @Inject si @Qualifier
     * @param method - reprezinta metoda dupa care se va scana
     * @return - Map<String,Bean> reprezinta toate dependintele metodei method
     */
    public static Map<String,Bean> scanForDependencies(Method method) {
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
                                    ret.put(beanAnnotation.value() != null ? beanAnnotation.value() : method.getName(),);

                                }
                            }

                        }


                    }
                }

            }

        }

        return ret;
    }




}
