package ro.teamnet.di;/*
* Person.java
*
* Copyright (c) 2013 Teamnet. All Rights Reserved.
*
* This source file may not be copied, modified or redistributed,
* in whole or in part, in any form or for any reason, without the express
* written consent of Teamnet.
*/

public class Person {

    private final Department department;

    public Person(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }
}
