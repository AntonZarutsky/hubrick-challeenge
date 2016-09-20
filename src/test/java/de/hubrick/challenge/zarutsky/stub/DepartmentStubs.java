package de.hubrick.challenge.zarutsky.stub;


import de.hubrick.challenge.zarutsky.domain.Department;

public class DepartmentStubs {

    public static Department dep(String name){
        return new Department(name);
    }

    public static Department d1(){
        return new Department("d1");
    }

    public static Department d2(){
        return new Department("d2");
    }

    public static Department d3(){
        return new Department("d3");
    }

    public static Department d4(){
        return new Department("d4");
    }

}
