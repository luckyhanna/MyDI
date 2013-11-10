package ro.teamnet.di;/*
* BeanBuilderTest.java
*
* Copyright (c) 2013 Teamnet. All Rights Reserved.
*
* This source file may not be copied, modified or redistributed,
* in whole or in part, in any form or for any reason, without the express
* written consent of Teamnet.
*/


import my.di.util.Bean;
import my.di.util.Scope;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanBuilderTest {

    MyConfiguration myConfiguration;

    Bean departmentBean;
    Map<String,Bean> dependencies;

    @Before
    public void setUp() throws Exception {
        myConfiguration = new MyConfiguration();
        dependencies = new HashMap<String, Bean>();
        Bean.BeanBuilder beanBuilder = Bean.builder();
        departmentBean = beanBuilder.withName("DEP1").build();
        dependencies.put("DEP1", departmentBean);
    }

    @Test
    public void testBeanBuilder() throws Exception {
        Bean.BeanBuilder builder = Bean.builder();
        builder.withName("pers1")
                .withType(Person.class)
                .withContainingClass(MyConfiguration.class)
                .withMethod(null)
                .withScope(Scope.SINGLETON)
                .withContainingObject(myConfiguration)
                .addDependency("DEP1",departmentBean);

        Bean bean = builder.build();

        Assert.assertEquals("pers1", bean.getName());
        Assert.assertEquals(Person.class,bean.getType());
        Assert.assertEquals(MyConfiguration.class,bean.getContainingClass());
        Assert.assertEquals(myConfiguration, bean.getContainingObject());
        Assert.assertEquals(Scope.SINGLETON, bean.getScope());
        Assert.assertEquals(dependencies, bean.getDependencies());

    }
}
