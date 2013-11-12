package my.di.container;/*
* ApplicationContextImpl.java
*
* Copyright (c) 2013 Teamnet. All Rights Reserved.
*
* This source file may not be copied, modified or redistributed,
* in whole or in part, in any form or for any reason, without the express
* written consent of Teamnet.
*/

import com.sun.jmx.remote.util.CacheMap;
import my.di.cache.CacheMapManager;
import my.di.util.Bean;
import my.di.util.Scanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContextImpl implements ApplicationContext {

    private Class configurationClass;
    
    private Map<String,Object> cacheMap = new HashMap<String, Object>();
    
//    private static CacheMapManager cacheMapManager = CacheMapManager.getInstance();

    private ApplicationContextImpl(Class configurationClass) {
        this.configurationClass = configurationClass;
    }

    public ApplicationContext factory(Class configurationClass) {
        return new ApplicationContextImpl(configurationClass);
    }

    public void initContainer() throws InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map<String,Bean> beans = Scanner.scanBeans(configurationClass);
        for (String name : beans.keySet()) {
            if (!cacheMap.containsKey(name)) {
                Bean bean = beans.get(name);
                Class containingClass = bean.getContainingClass();
                Method method = bean.getMethod();
//                containingClass.

            }
        }
    }

    @Override
    public Object getBean(String name) {
//       if 
        return null;
    }

    @Override
    public Object getBean(Class type) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }



}



