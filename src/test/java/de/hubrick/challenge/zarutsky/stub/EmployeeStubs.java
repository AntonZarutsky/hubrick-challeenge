package de.hubrick.challenge.zarutsky.stub;


import de.hubrick.challenge.zarutsky.domain.Department;
import de.hubrick.challenge.zarutsky.domain.Employee;
import java.math.BigDecimal;

import static de.hubrick.challenge.zarutsky.stub.DepartmentStubs.d1;
import static de.hubrick.challenge.zarutsky.stub.DepartmentStubs.d2;
import static java.math.BigDecimal.valueOf;

public class EmployeeStubs {

    public static Employee employee(int age, BigDecimal income, Department department){
        return Employee.builder()
                .department(department)
                .age(age)
                .income(income)
                .build();
    }

    public static Employee e1_d1(){
        return employee(9, valueOf(100), d1());
    }

    public static Employee e2_d1(){
        return employee(15, valueOf(200), d1());
    }
    public static Employee e3_d1(){
        return employee(16, valueOf(250), d1());
    }

    public static Employee e1_d2(){
        return employee(10, valueOf(100), d2());
    }

    public static Employee e2_d2(){
        return employee(15, valueOf(200), d2());
    }

    public static Employee e3_d2(){
        return employee(26, valueOf(210), d2());
    }

    public static Employee e4_d2(){
        return employee(16, valueOf(50), d2());
    }

    public static Employee e5_d2(){
        return employee(49, valueOf(300), d2());
    }

    public static Employee e6_d2(){
        return employee(18, valueOf(150), d2());
    }


}


