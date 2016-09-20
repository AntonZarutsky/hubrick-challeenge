package de.hubrick.challenge.zarutsky.service;


import de.hubrick.challenge.zarutsky.domain.Employee;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 * Report generator class. contains generic methods, to calculate different types of report.
 */
public class ReportService {

    /**
     * Median is 50 percentile. With result grouping
     *
     * @param employees Collection of employees, which is the basis for report calculation
     * @param valueFunction Function which is used to determine, what data to use for aggregation
     * @param byFunction Function which is used for grouping results
     * @return Map<BY, Double>  aggregated output
     */
    public static <VAL extends Number, BY> Map<BY, Double> median(List<Employee> employees,
                                                                  Function<Employee, VAL> valueFunction,
                                                                  Function<Employee, BY> byFunction) {

        return percentile(employees, valueFunction, byFunction, 50);
    }

    /**
     * 95 percentile with result grouping.
     *
     * @param employees Collection of employees, which is the basis for report calculation
     * @param valueFunction Function which is used to determine, what data to use for aggregation
     * @param byFunction Function which is used for grouping results
     * @return Map<BY, Double>  aggregated output
     */
    public static <VAL extends Number, BY> Map<BY, Double> p95(List<Employee> employees,
                                                               Function<Employee, VAL> valueFunction,
                                                               Function<Employee, BY> byFunction) {

        return percentile(employees, valueFunction, byFunction, 95);
    }

    /**
     * 99 percentile with result grouping.
     *
     * @param employees Collection of employees, which is the basis for report calculation
     * @param valueFunction Function which is used to determine, what data to use for aggregation
     * @param byFunction Function which is used for grouping results
     * @return Map<BY, Double> aggregated output
     */
    public static <VAL extends Number, BY> Map<BY, Double> p99(List<Employee> employees,
                                                               Function<Employee, VAL> valueFunction,
                                                               Function<Employee, BY> byFunction) {

        return percentile(employees, valueFunction, byFunction, 99);
    }

    /**
     * avgWithStep. Grouping result by ranges of values with defined step.
     *
     * @param employees Collection of employees, which is the basis for report calculation
     * @param valueFunction Function which is used to determine, what data to use for aggregation
     * @param byFunction Function which is used for grouping results
     * @param step range size.
     * @return Map<BY, Double>  aggregated output
     */
    public static <VAL extends Number, BY extends Number> Map<Integer, Double> avgWithStep(List<Employee> employees,
                                                                                           Function<Employee, VAL> valueFunction,
                                                                                           Function<Employee, BY> byFunction,
                                                                                           int step) {
        return
            employees.stream()
                .collect(
                    groupingBy(
                        employee -> ReportService.getPartitionId(byFunction.apply(employee), step),
                            mapping(
                                valueFunction,
                                Collectors.averagingDouble(
                                    value -> asDouble(value)
                                )
                            )
                        )
                    );
    }


    private static <VAL extends Number> Double asDouble(VAL value) {
        return new Double(value.toString());
    }

    private static <BY extends Number> Integer getPartitionId(BY partitionBy, int step) {
        return new Double(asDouble(partitionBy) / step).intValue();
    }


    /**
     * N percentile with result grouping.
     *
     * @param employees Collection of employees, which is the basis for report calculation
     * @param valueFunction Function which is used to determine, what data to use for aggregation
     * @param byFunction Function which is used for grouping results
     * @return Map<BY, Double> aggregated output
     */
    public static <VAL extends Number, BY> Map<BY, Double> percentile(List<Employee> employees,
                                                                      Function<Employee, VAL> valueFunction,
                                                                      Function<Employee, BY> byFunction,
                                                                      double percentile) {

        return
            employees.stream()
                 .collect(
                     groupingBy(
                         byFunction,
                         collectingAndThen(
                             mapping(
                                 valueFunction,
                                 toList()
                             ),
                             values -> percentile(values, percentile)
                         )
                     ));
    }

    /**
     * N percentile for list without aggregation
     * @param values collection of values for analys
     * @param percentile
     * @param <VAL extends Number> values should inherit from class Number.
     * @return N percentile
     */
    public static <VAL extends Number> double percentile(List<VAL> values, double percentile) {

//      sorting in natural order
        values.sort(null);

//      finding out approximate index of list
        double approxIndex = percentile * (values.size() + 1) / 100;
        final int index = (int) approxIndex;

//      difference coefficient between 2 closest index, wrapping approxIndex
        double difference = approxIndex - index;

//      corner cases
        if (index == 0) {
            return asDouble(values.get(0));
        }
        if (index >= values.size()) {
            return asDouble(values.get(values.size() - 1));
        }

        Double lowerValue = asDouble(values.get(index - 1));
        Double upperValue = asDouble(values.get(index));

//      percentile calculation.
        return lowerValue + difference * (upperValue - lowerValue);
    }

}
