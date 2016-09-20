package de.hubrick.challenge.zarutsky.utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileUtils {

    private FileUtils() {}

    public static String getPath(String folderPath) {
// if tilda is used, we replacing it with proper absolute path to user home folder
        return  folderPath.replaceFirst("^~", System.getProperty("user.home"));
    }

    public static void createFolder(String folderOut) {
        try {
            if (Files.notExists(Paths.get(folderOut))) {
                Files.createDirectory(Paths.get(folderOut));
            }
        }catch (IOException e) {
            throw new ProcessingException("unable to create output Folder: " + folderOut);
        }
    }
}
