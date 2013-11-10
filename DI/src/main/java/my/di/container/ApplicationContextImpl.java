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

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextImpl implements ApplicationContext {

    private Class configurationClass;
    
    private Map<String,Bean> cacheMap = new HashMap<String, Bean>();

    private ApplicationContextImpl(Class configurationClass) {
        this.configurationClass = configurationClass;
    }

    public ApplicationContext factory(Class configurationClass) {
        return new ApplicationContextImpl(configurationClass);
    }

    public void initContainer() throws InstantiationException, IllegalAccessException {
        Map<String,Bean> beans = Scanner.scanForBeans(configurationClass);
        for (String s : beans.keySet()) {
            Bean bean = beans.get(s);
            Map<String,Bean> dependencies = Scanner.scanForDependencies(bean.getMethod());

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



