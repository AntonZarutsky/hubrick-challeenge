package de.hubrick.challenge.zarutsky.service;


import de.hubrick.challenge.zarutsky.domain.Department;
import de.hubrick.challenge.zarutsky.domain.Employee;
import de.hubrick.challenge.zarutsky.dto.ReportDto;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


public final class DataProcessor {

    private DataProcessor() { }

    private static ReportWriter reportWriter = new CsvReportWriter();

    public static void processData(String folderIn, String folderOut){

        Map<String, Integer> ages = DataRepository.getAges(Paths.get(folderIn, "ages.csv"));
        Map<Integer, Department> departments = DataRepository.getDepartments(Paths.get(folderIn,"departments.csv"));

        List<Employee> employees = DataRepository.getEmployees(Paths.get(folderIn, "employees.csv"), ages, departments);

        reportWriter.write(
                ReportDto.from("Department", "Income (median)"),
                incomeMedianByDepartmentReport(employees),
                Paths.get(folderOut, "income-by-department.csv"));

        reportWriter.write(
                ReportDto.from("Department", "Income (p95)"),
                incomeP95ByDepartmentReport(employees),
                Paths.get(folderOut, "income-95-by-department.csv"));

        reportWriter.write(
                ReportDto.from("Age", "Income (avg)"),
                incomeAvgByAgeWithRangeReport(employees, 10),
                Paths.get(folderOut, "income-average-by-age-range.csv"));

        reportWriter.write(
                ReportDto.from("Department", "Age (median)"),
                ageMedianByDepartmentReport(employees),
                Paths.get(folderOut, "employee-age-by-department.csv"));
    }


    private static List<ReportDto> incomeMedianByDepartmentReport(List<Employee> employees) {
        return ReportService
                .median(
                    employees,
                    Employee::getIncome,
                    Employee::getDepartment)
                .entrySet()
                .stream()
                .map(entry -> ReportDto.from(entry.getKey().getName(),
                                             formatDouble(entry.getValue())))
                .collect(toList());
    }
    private static List<ReportDto> incomeP95ByDepartmentReport(List<Employee> employees) {
        return ReportService
                .p95(
                    employees,
                    Employee::getIncome,
                    Employee::getDepartment)
                .entrySet()
                .stream()
                .map(entry -> ReportDto.from(entry.getKey().getName(),
                                             formatDouble(entry.getValue())))
                .collect(toList());
    }
    private static List<ReportDto> incomeAvgByAgeWithRangeReport(List<Employee> employees, int step) {
        return ReportService
                .avgWithStep(
                    employees,
                    Employee::getIncome,
                    Employee::getAge,
                        step)
                .entrySet()
                .stream()
                .map(entry -> ReportDto.from(toRangeString(entry.getKey(), step),
                                             formatDouble(entry.getValue())))
                .collect(toList());
    }

    private static String toRangeString(Integer key, int step) {
        return new StringBuilder().append(key * step)
                                  .append("-")
                                  .append((key + 1) * step - 1)
                                  .toString();

    }

    private static List<ReportDto> ageMedianByDepartmentReport(List<Employee> employees) {
        return ReportService
                .median(
                    employees,
                    Employee::getAge,
                    Employee::getDepartment)
                .entrySet()
                .stream()
                .map(entry -> ReportDto.from(entry.getKey().getName(),
                                             formatDouble(entry.getValue())))
                .collect(toList());
    }


//  aggregated results in reports are taken with precision 2.
    private static String formatDouble(Double value) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP)
                                    .toString();
    }


}
