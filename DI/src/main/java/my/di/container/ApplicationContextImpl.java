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

import java.lang.reflect.InvocationTargetException;
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

    public static ApplicationContext factory(Class configurationClass) {
        return new ApplicationContextImpl(configurationClass);
    }

    @Override
    public void initContainer() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String,Bean> beans = Scanner.scanBeans(configurationClass);
        for (String name : beans.keySet()) {
            if (!cacheMap.containsKey(name)) {
                Bean bean = beans.get(name);
                
                Object obj = instantiate(bean);
                cacheMap.put(name, obj);
            }
        }
        System.out.println(cacheMap);
    }

    private Object instantiate(Bean bean) throws InvocationTargetException, IllegalAccessException {

        Object containingObject = bean.getContainingObject();
        Method method = bean.getMethod();
        Object obj;
        if (bean.getDependencies() == null) {
            obj = method.invoke(containingObject);
        } else {
            Map<String,Bean> dependencies = bean.getDependencies();
            Object[] params = new Object[dependencies.size()];
            int i = 0;
            for (String s : dependencies.keySet()) {
                params[i] = instantiate(dependencies.get(s));
                i++;
            }
            obj = method.invoke(containingObject, params);
        }
        return obj;
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



