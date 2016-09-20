package de.hubrick.challenge.zarutsky.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Employee {

    private String name;

    private Gender gender;

    private BigDecimal income;

    private Department department;

    private int age;


    private Employee(Builder builder) {
        // Here can also be validation on wrong parameters / missing values.

        this.age = builder.age;
        this.department = builder.department;
        this.name = builder.name;
        this.gender = builder.gender;
        this.income = builder.income;
    }

    public Department getDepartment() {
        return department;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public static Employee.Builder builder() {
        return new Builder();
    }

//  I used to use builder if model object contains 5+ fields. Handy to work with it.
    public static class Builder {

        private Builder() { }

        private Department department;

        private int age;

        private String name;

        private Gender gender;

        private BigDecimal income;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder income(BigDecimal income) {
            this.income = income;
            return this;
        }

        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Employee build(){
            return new Employee(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
