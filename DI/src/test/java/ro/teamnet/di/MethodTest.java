package ro.teamnet.di;/*
* MethodTest.java
*
* Copyright (c) 2013 Teamnet. All Rights Reserved.
*
* This source file may not be copied, modified or redistributed,
* in whole or in part, in any form or for any reason, without the express
* written consent of Teamnet.
*/

import my.di.annotations.Qualifier;
import my.di.util.Bean;
import my.di.util.Scanner;
import org.junit.Assert;
import org.junit.Test;
import sun.reflect.annotation.AnnotationParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class MethodTest {

    @Test
    public void testMethodMethods() throws Exception {
        Method method = MyConfiguration.class.getDeclaredMethod("team1",Department.class,String.class);
        Annotation[][] annotations = method.getParameterAnnotations();
        Class[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i <annotations.length; i++) {
            if (annotations[i].length > 0) {
                System.out.println(parameterTypes[i]);
//                annotations[i]
            }
        }
    }

    @Test
    public void testDependencies() throws Exception {
        Method method = MyConfiguration.class.getDeclaredMethod("team1",Department.class,Person.class);
        Map<String,Bean> dependencies = Scanner.scanDependencies(method);
        Assert.assertNotNull(dependencies);
    }

    @Test
    public void testBeans() throws Exception {
        Map<String,Bean> beans = Scanner.scanBeans(MyConfiguration.class);
        Assert.assertNotNull(beans);
    }
}
