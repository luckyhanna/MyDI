package ro.teamnet.di;/*
* MyConfigTest.java
*
* Copyright (c) 2013 Teamnet. All Rights Reserved.
*
* This source file may not be copied, modified or redistributed,
* in whole or in part, in any form or for any reason, without the express
* written consent of Teamnet.
*/

import my.di.annotations.Bean;
import my.di.annotations.Qualifier;

import javax.inject.Inject;


@Configuration
public class MyConfiguration {

    @Bean("DEP1")
    public Department department1() {
        return new Department("DEP1");
    }

    @Bean("DEP2")
    public Department department2() {
        return new Department("DEP2");
    }

    @Bean("pers1")
    @Inject
    public Person person1(@Qualifier("DEP1") Department department) {
        return new Person(department);
    }

    @Bean("pers2")
    @Inject
    public Person person2(@Qualifier("DEP2") Department department) {
        return new Person(department);
    }

    @Bean("team1")
    @Inject
    public Team team1(@Qualifier("DEP1") Department department,@Qualifier("pers2") Person person, String name) {
        return new Team(department, name);
    }

}
