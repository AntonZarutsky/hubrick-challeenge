package de.hubrick.challenge.zarutsky.utils;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfiguration {

    private LoggerConfiguration() {}

    public static void init(String folder) throws IOException {

        final Logger parentLogger = Logger.getAnonymousLogger().getParent();


        // reducing logging level for default console handler to info
        // errors will be added only ot errors.log file (described below)
        updateDefaultHandlersConfiguration(parentLogger);

        FileHandler errorFileLog = new FileHandler(Paths.get(folder, "errors.log").toString());
        errorFileLog.setFormatter(new SimpleFormatter());
        errorFileLog.setLevel(Level.SEVERE);

        parentLogger.addHandler(errorFileLog);

    }

    private static void updateDefaultHandlersConfiguration(Logger parentLogger) {
        Handler[] handlers = parentLogger.getHandlers();
        if (handlers == null) {
            return;
        }
        for (Handler handler: handlers){
            parentLogger.removeHandler(handler);
        }
    }

}
