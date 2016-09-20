package de.hubrick.challenge.zarutsky.utils;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class InputValidator {

    private InputValidator() {}

    private static final String[] DATA_FILE_NAMES = new String[] {"ages.csv", "departments.csv", "employees.csv"};

    public static void validate(String folderPath) {

        if (Files.notExists(Paths.get(folderPath))) {
            throw new ProcessingException("data folder does not exists");
        }

        for (String dataFile : DATA_FILE_NAMES) {
            Path path = Paths.get(folderPath, dataFile);
            if (Files.notExists(path)) {
                throw new ProcessingException("data file " + path + " does not exists");
            }
        }
    }

}
