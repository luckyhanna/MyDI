package my.di.util;/*
* Bean.java
*
* Copyright (c) 2013 Teamnet. All Rights Reserved.
*
* This source file may not be copied, modified or redistributed,
* in whole or in part, in any form or for any reason, without the express
* written consent of Teamnet.
*/

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bean {

    private Bean(BeanBuilder beanBuilder) {

        this.name = beanBuilder.name;
        this.type = beanBuilder.type;
        this.method = beanBuilder.method;
        this.scope = beanBuilder.scope;
        this.containingObject = beanBuilder.containingObject;
        this.containingClass = beanBuilder.containingClass;
        this.dependencies = beanBuilder.dependencies;
    }

    private final String name;

    private final Class type;

    private final Method method;

    private final Scope scope;

    private final Object containingObject;

    private final Class containingClass;

    private final Map<String,Bean> dependencies;

    public String getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    public Method getMethod() {
        return method;
    }

    public Scope getScope() {
        return scope;
    }

    public Object getContainingObject() {
        return containingObject;
    }

    public Class getContainingClass() {
        return containingClass;
    }

    public Map<String,Bean> getDependencies() {
        return dependencies;
    }

    public static BeanBuilder builder() {
        return new BeanBuilder();
    }

    public static class BeanBuilder {

        private String name;
        private Class type;
        private Method method;
        private Scope scope;
        private Object containingObject;
        private Class containingClass;
        private Map<String,Bean> dependencies = new HashMap<String, Bean>();

        private BeanBuilder() {
        }


        public BeanBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public BeanBuilder withType(Class type) {
            this.type = type;
            return this;
        }

        public BeanBuilder withMethod(Method method) {
            this.method = method;
            return this;
        }

        public BeanBuilder withScope(Scope scope) {
            this.scope = scope;
            return this;
        }

        public BeanBuilder withContainingObject(Object containingObject) {
            this.containingObject = containingObject;
            return this;
        }

        public BeanBuilder withContainingClass(Class containingClass) {
            this.containingClass = containingClass;
            return this;
        }

        public BeanBuilder addDependency(String name, Bean bean) {
            this.dependencies.put(name, bean);
            return this;
        }
        
        public BeanBuilder withDependencies(Map<String,Bean> dependencyMap) {
            this.dependencies = dependencyMap;
            return this;
        }

        public Bean build() {

            return new Bean(this);

        }

    }

}
