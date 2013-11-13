package my.di.container;/*
* ApplicationContext.java
*
* Copyright (c) 2013 Teamnet. All Rights Reserved.
*
* This source file may not be copied, modified or redistributed,
* in whole or in part, in any form or for any reason, without the express
* written consent of Teamnet.
*/

import java.lang.reflect.InvocationTargetException;

public interface ApplicationContext {

    void initContainer() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    Object getBean(String name);

    Object getBean(Class type);

}
