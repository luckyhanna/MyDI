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
import org.junit.Test;
import sun.reflect.annotation.AnnotationParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

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
}
