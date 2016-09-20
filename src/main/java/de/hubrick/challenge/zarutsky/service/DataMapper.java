package de.hubrick.challenge.zarutsky.service;


import de.hubrick.challenge.zarutsky.domain.Department;
import de.hubrick.challenge.zarutsky.domain.Employee;
import de.hubrick.challenge.zarutsky.domain.Gender;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Optional.empty;



public final class DataMapper {

    private static Logger LOGGER = Logger.getLogger(DataMapper.class.getName());

    public static Optional<Department> toDepartment(String line){
        return Optional.of(new Department(line));
    }

    public static Optional<Employee> toEmployee(String line, Map<Integer, Department> departments, Map<String, Integer> ages){
        String[] tokens = line.split(",");

//      Usually Sonar write on this something like "What is the magic number" ;)
        if (tokens.length != 4){
            return empty();
        }

        Employee.Builder builder = Employee.builder();

        builder.name(tokens[1]);

        try {
            builder.income(
                new BigDecimal(tokens[3]));
        } catch (NumberFormatException e) {
//          if some information about employee is missing,- it's better to skip it's processing. and report with an error,
//          rather than take it into account.
//          This could prevent unpredicted results
            LOGGER.severe("Unable to parse income from line:  " + line);

//          Empty result will be filtered afterwards
            return empty();
        }

        Optional<Gender> genderOpt = Gender.byShortName(tokens[2]);
        if (genderOpt.isPresent()) {
            builder.gender(genderOpt.get());
        } else {
            LOGGER.severe("Unable to parse gender from line:  " + line);
            return empty();
        }

        if (ages.containsKey(tokens[1])) {
            builder.age(ages.get(tokens[1]));
        } else {
            LOGGER.severe("Unable to parse gender from line:  " + line);
            return empty();
        }

        try {
            Integer departmentId = Integer.parseInt(tokens[0]);
            if (departments.containsKey(departmentId)) {
                builder.department(departments.get(departmentId));
            } else {
                LOGGER.severe("Unable to parse gender from line:  " + line);
                return empty();
            }
        } catch (NumberFormatException e) {
            LOGGER.severe("Unable to parse income from line:  " + line);
            return empty();
        }

        return Optional.of(builder.build());
    }

    // validating income values for age
    public static Optional<String[]> toAgeByEmployee(String line) {
        String[] tokens = line.split(",");

        if (tokens.length != 2){
            return empty();
        }
        try{
//          Checking if the second token is f Number.
//          TODO also range could be checked ex. (16..150)
            Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e){
            return empty();
        }

        return  Optional.of(tokens);
    }
}














