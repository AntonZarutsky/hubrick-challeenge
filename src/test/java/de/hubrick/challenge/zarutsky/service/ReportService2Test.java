package de.hubrick.challenge.zarutsky.service;

import de.hubrick.challenge.zarutsky.domain.Department;
import de.hubrick.challenge.zarutsky.domain.Employee;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import static de.hubrick.challenge.zarutsky.stub.DepartmentStubs.d1;
import static de.hubrick.challenge.zarutsky.stub.DepartmentStubs.d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e1_d1;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e1_d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e2_d1;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e2_d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e3_d1;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e3_d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e4_d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e5_d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e6_d2;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class ReportService2Test {


    @Test
    public void calculateAverageByAge(){

        Map<Integer, BigDecimal> actual =
                ReportService.avgWithStep(fillEmployees(),
                        Employee::getIncome,
                        Employee::getAge,
                        10)
                .entrySet().stream()
                .collect(
                    toMap(entry -> entry.getKey(),
                          entry -> bigDecimal(entry.getValue()))
                );

        Map<Integer, BigDecimal> expected = new HashMap();
        expected.put(0, bigDecimal(100));
        expected.put(1, bigDecimal(158.3333));
        expected.put(2, bigDecimal(210));
        expected.put(4, bigDecimal(300));


        assertThat(actual, equalTo(expected));


    }

    @Test
    public void calculatePercentile75(){

        Map<Department, BigDecimal> actual =
                ReportService.percentile(fillEmployees(),
                        Employee::getIncome,
                        Employee::getDepartment,
                        75)
                        .entrySet().stream()
                        .collect(
                                toMap(entry -> entry.getKey(),
                                        entry -> bigDecimal(entry.getValue()))
                        );

        Map<Department, BigDecimal> expected = new HashMap();
        expected.put(d1(), bigDecimal(250));
        expected.put(d2(), bigDecimal(232.5));


        assertThat(actual, equalTo(expected));
    }

    @Test
    public void calculateMedian(){

        Map<Department, BigDecimal> actual =
                ReportService.median(fillEmployees(),
                        Employee::getAge,
                        Employee::getDepartment
                        )
                        .entrySet().stream()
                        .collect(
                                toMap(entry -> entry.getKey(),
                                        entry -> bigDecimal(entry.getValue()))
                        );

        Map<Department, BigDecimal> expected = new HashMap();
        expected.put(d1(), bigDecimal(15));
        expected.put(d2(), bigDecimal(17));


        assertThat(actual, equalTo(expected));
    }


    private BigDecimal bigDecimal(double value) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private List<Employee> fillEmployees() {
        return Arrays.<Employee>asList(
                e1_d1(),
                e2_d1(),
                e3_d1(),
                e1_d2(),
                e2_d2(),
                e3_d2(),
                e4_d2(),
                e5_d2(),
                e6_d2()
        );

    }





}