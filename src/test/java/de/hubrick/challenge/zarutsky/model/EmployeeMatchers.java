package de.hubrick.challenge.zarutsky.model;


import de.hubrick.challenge.zarutsky.domain.Department;
import de.hubrick.challenge.zarutsky.domain.Employee;
import de.hubrick.challenge.zarutsky.domain.Gender;
import java.math.BigDecimal;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class EmployeeMatchers {

    public static FeatureMatcher<Employee, String> hasName(final Matcher<String> matcher) {
        return new FeatureMatcher<Employee, String>(matcher, "name" , "name") {
            @Override
            protected String featureValueOf(Employee actual) {
                return actual.getName();
            }
        };
    }
    public static FeatureMatcher<Employee, Department> hasDepartment(final Matcher<Department> matcher) {
        return new FeatureMatcher<Employee, Department>(matcher, "department" , "department") {
            @Override
            protected Department featureValueOf(Employee actual) {
                return actual.getDepartment();
            }
        };
    }
    public static FeatureMatcher<Employee, Gender> hasGender(final Matcher<Gender> matcher) {
        return new FeatureMatcher<Employee, Gender>(matcher, "gender" , "gender") {
            @Override
            protected Gender featureValueOf(Employee actual) {
                return actual.getGender();
            }
        };
    }

    public static FeatureMatcher<Employee, Integer> hasAge(final Matcher<Integer> matcher) {
        return new FeatureMatcher<Employee, Integer>(matcher, "age" , "age") {
            @Override
            protected Integer featureValueOf(Employee actual) {
                return actual.getAge();
            }
        };
    }

    public static FeatureMatcher<Employee, BigDecimal> hasIncome(final Matcher<BigDecimal> matcher) {
        return new FeatureMatcher<Employee, BigDecimal>(matcher, "income" , "income") {
            @Override
            protected BigDecimal featureValueOf(Employee actual) {
                return actual.getIncome();
            }
        };
    }

}
