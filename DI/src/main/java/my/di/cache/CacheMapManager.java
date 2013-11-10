package my.di.cache;/*
* CacheMapManager.java
*
* Copyright (c) 2013 Teamnet. All Rights Reserved.
*
* This source file may not be copied, modified or redistributed,
* in whole or in part, in any form or for any reason, without the express
* written consent of Teamnet.
*/

import java.util.HashMap;
import java.util.Map;

public class CacheMapManager {
    
    private static CacheMapManager cacheMapManagerInstance = new CacheMapManager();

    private Map<String, Class> beanNames = new HashMap<String, Class>();
    
    private Map<Class, Object> singletons = new HashMap<Class, Object>();
    
    public static CacheMapManager getInstance() {
        return cacheMapManagerInstance;
    }

    public Map<Class, Object> getSingletons() {
        return singletons;
    }

    public Map<String, Class> getBeanNames() {
        return beanNames;
    }

    public Object getBean(String beanName) throws InstantiationException, IllegalAccessException {
        Class clazz = beanNames.get(beanName);
        if (clazz == null) {
            throw new InstantiationException("Unknown bean name " + beanName);
        }
        return getBean(clazz);
    }
    
    public Object getBean(Class clazz) throws InstantiationException, IllegalAccessException {
        Object bean = null;
        
        if (singletons.containsKey(clazz)) {
            Object singletonObject = singletons.get(clazz);
            if (singletonObject == null) {
                singletonObject = instantiateObject(clazz);
                singletons.put(clazz, singletonObject);
            }
            
            bean = singletonObject;            
        } else {
            bean = instantiateObject(clazz);
        }
        
        return bean;
    }

    private Object instantiateObject(Class clazz) throws IllegalAccessException, InstantiationException {
        Object instance = null;
        
        instance = clazz.newInstance();


        return instance;
    }

}
