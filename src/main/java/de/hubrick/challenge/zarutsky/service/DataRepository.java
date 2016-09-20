package de.hubrick.challenge.zarutsky.service;


import de.hubrick.challenge.zarutsky.domain.Department;
import de.hubrick.challenge.zarutsky.domain.Employee;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public final class DataRepository {

    private static Logger LOGGER = Logger.getLogger(DataRepository.class.getName());

    public static Map<String, Integer> getAges(Path path) {
        try(Stream<String> stream = Files.lines(path, Charset.forName("UTF-8"))) {
//          assuming that this file can contain duplicates (different rows for the same toEmployee)
            return getAgesByEmployee(stream);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to parse ages.csv file ",  e);
            throw new RuntimeException("");
        }
    }

    private static Map<String, Integer> getAgesByEmployee(Stream<String> stream) {
        return
            stream.map(DataMapper::toAgeByEmployee)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(
                Collectors.toMap(tokens -> tokens[0],
                    tokens -> Integer.parseInt(tokens[1]),
//      in case if file contains duplicate, we will take first value
                    (a, b) -> a)
            );
    }


    public static Map<Integer, Department> getDepartments(Path path) {
        try(Stream<String> stream = Files.lines(path, Charset.forName("UTF-8"))) {
//      assuming that this file can contain duplicates (different rows for the same toEmployee)
            AtomicInteger counter = new AtomicInteger(0);
            return stream
                .map(DataMapper::toDepartment)
                .filter(Optional::isPresent)
//      In principle Optional::get coould be moved incide toMap as d -> d.get
//      but I think it looks for more clear.
                .map(Optional::get)
                .collect(
                    toMap(d -> counter.incrementAndGet(),
                          d -> d,
//      in case if file contains duplicate, we will take first value
                          (a, b) -> a));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to parse departments.csv file ",  e);
            throw new RuntimeException("");
        }
    }

    public static List<Employee> getEmployees(Path path, Map<String, Integer> ages, Map<Integer, Department> departments){
        List<Employee> employees;
        try(Stream<String> stream = Files.lines(path, Charset.forName("UTF-8"))) {
            //      assuming that this file can contain duplicates (different rows for the same toEmployee)
            return stream
                    .map(line -> DataMapper.toEmployee(line, departments, ages))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(toList());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to parse employees.csv file ",  e);
            throw new RuntimeException();
        }
    }


}
