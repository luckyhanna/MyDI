package ro.teamnet.di;/*
* Team.java
*
* Copyright (c) 2013 Teamnet. All Rights Reserved.
*
* This source file may not be copied, modified or redistributed,
* in whole or in part, in any form or for any reason, without the express
* written consent of Teamnet.
*/

public class Team {

    private Department department;
    private String name;

    public Team(Department department, String name) {
        this.department = department;
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }
}
