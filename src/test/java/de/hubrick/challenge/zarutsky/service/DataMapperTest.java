package de.hubrick.challenge.zarutsky.service;

import de.hubrick.challenge.zarutsky.domain.Department;
import de.hubrick.challenge.zarutsky.domain.Employee;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;

import static de.hubrick.challenge.zarutsky.domain.Gender.MALE;
import static de.hubrick.challenge.zarutsky.model.EmployeeMatchers.hasAge;
import static de.hubrick.challenge.zarutsky.model.EmployeeMatchers.hasDepartment;
import static de.hubrick.challenge.zarutsky.model.EmployeeMatchers.hasGender;
import static de.hubrick.challenge.zarutsky.model.EmployeeMatchers.hasIncome;
import static de.hubrick.challenge.zarutsky.model.EmployeeMatchers.hasName;
import static de.hubrick.challenge.zarutsky.stub.DepartmentStubs.d1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DataMapperTest {

    @Test
    public void parseAgesByEmployees(){
        Optional<String[]> actual = DataMapper.toAgeByEmployee("Anton,15");

        assertThat(actual.isPresent(), equalTo(true));
        assertThat(actual.get(), equalTo(new String[]{"Anton", "15"}));

    }

    @Test
    public void parseAgesByEmployeesWithWrongAge(){
        Optional<String[]> actual = DataMapper.toAgeByEmployee("Anton,15-17");

        assertThat(actual.isPresent(), equalTo(false));
    }


    @Test
    public void mapEmployeeWithCorrectData(){

        Optional<Employee> actual = DataMapper.toEmployee("1,Anton,m,250", departments(1, d1()), ages("Anton", 12));

        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get(),
                   allOf(
                        Arrays.asList(
                            hasName(equalTo("Anton")),
                            hasAge(equalTo(12)),
                            hasDepartment(equalTo(d1())),
                            hasGender(equalTo(MALE)),
                            hasIncome(equalTo(new BigDecimal(250)))
                        )
        ));
    }

    @Test
    public void mapEmployeeWithMissingDepartment(){

        Optional<Employee> actual = DataMapper.toEmployee("1,Anton,m,250", departments(2, d1()), ages("Anton", 12));

        assertThat(actual.isPresent(), is(false));
    }

    @Test
    public void mapEmployeeWithMissingAge(){

        Optional<Employee> actual = DataMapper.toEmployee("1,Anton,m,250", departments(1, d1()), ages("Anton2222", 12));

        assertThat(actual.isPresent(), is(false));
    }

    @Test
    public void mapEmployeeWithWrongGender(){

        Optional<Employee> actual = DataMapper.toEmployee("1,Anton,xxx,250", departments(1, d1()), ages("Anton", 12));

        assertThat(actual.isPresent(), is(false));
    }

    @Test
    public void mapEmployeeWithWrongIncome(){

        Optional<Employee> actual = DataMapper.toEmployee("1,Anton,m,wow_its_for_me!!!!", departments(1, d1()), ages("Anton", 12));

        assertThat(actual.isPresent(), is(false));
    }

    private Map<String, Integer> ages(String name, Integer age) {
        Map<String, Integer> ages = new HashMap();
        ages.put(name, age);
        return ages;
    }

    private Map<Integer, Department> departments(int id, Department department) {
        Map<Integer, Department> departments = new HashMap();
        departments.put(id, department);
        return departments;
    }


}