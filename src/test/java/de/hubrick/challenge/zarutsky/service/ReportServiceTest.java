package de.hubrick.challenge.zarutsky.service;

import de.hubrick.challenge.zarutsky.domain.Employee;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e1_d1;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e1_d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e2_d1;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e2_d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e3_d1;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e3_d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e4_d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e5_d2;
import static de.hubrick.challenge.zarutsky.stub.EmployeeStubs.e6_d2;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.runners.Parameterized.*;


@RunWith(Enclosed.class)
public class ReportServiceTest {

    @RunWith(Parameterized.class)
    public static class TestPercentileCalcultion {

        @Parameter
        public Double[] values;

        @Parameter(value = 1)
        public double percentile;

        @Parameter(value = 2)
        public BigDecimal expected;

        @Parameters
//        expected values are calculated
        public static Collection parameters() {
            return Arrays.asList(new Object[][] {
                    { new Double[]{1.0, 20.0, 155.0, 40.0, 3.0, 245.0, 18.0} , new Double(50), new BigDecimal(20)},
                    { new Double[]{1.0, 20.0, 40.0, 3.0, 245.0, 18.0, 300.0, 301.0} , new Double(75), new BigDecimal(286.25) },
                    { new Double[]{50.0, 100.0, 150.0, 160.0, 210.0, 300.0, 400.0} , new Double(50), new BigDecimal(160) },
                    { new Double[]{50.0, 100.0, 150.0, 160.0, 210.0, 300.0, 400.0} , new Double(95), new BigDecimal(400) }
            });
        }

        @Test
        public void calculatePercentile(){
            double actual = ReportService.percentile(Arrays.asList(values), percentile);

            assertThat(new BigDecimal(actual), equalTo(expected));
        }

        private static List<Double> toList(Double... arguments) {
            List<Double> list = new ArrayList<>();
            for (Double value: arguments){
                list.add(value);
            }
            return list;
        }
    }

    @RunWith(Parameterized.class)
    public static class TestGroupingWithStep {

        @Parameter
        public Employee[] employees;

        @Parameter(value = 1)
        public Integer step;

        @Parameter(value = 2)
        public Integer[] partitiones;

        @Parameters
        public static Collection parameters() {
            return Arrays.asList(new Object[][] {
                    { new Employee[]{e1_d1(), e2_d1(), e1_d2()} , 5, new Integer[]{1,2,3}},
//                     9 ,15 ,10
                    { new Employee[]{e5_d2(), e3_d2(), e1_d2()} , 10, new Integer[]{1,2,4}}
//                    10, 26, 49

            });
        }

        @Test
        public void calculateAverageByAge(){
            List<Employee> employeesList = Arrays.asList(employees);

            Map<Integer, Double> actual =
                    ReportService.avgWithStep(employeesList,
                            Employee::getIncome,
                            Employee::getAge,
                            step);

            Set<Integer> expected = new HashSet<>(Arrays.asList(partitiones));

            assertThat(actual.keySet(), equalTo(expected));


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






}