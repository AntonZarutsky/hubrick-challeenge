package de.hubrick.challenge.zarutsky;

import de.hubrick.challenge.zarutsky.service.DataProcessor;
import de.hubrick.challenge.zarutsky.utils.FileUtils;
import de.hubrick.challenge.zarutsky.utils.InputValidator;
import de.hubrick.challenge.zarutsky.utils.LoggerConfiguration;
import de.hubrick.challenge.zarutsky.utils.ProcessingException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application entry point.
 * Data
 */
public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    private static final String OUTPUT_FOLDER = "out";

    public static void main(String[] args){
        try {
            // I've restricted to only one parameter - path to data folder
            // this path is also be used for output data for simplicity
            if (args == null || args.length != 1){
                LOGGER.severe("path to data files was not passed as parameter");
            }

            String folderPath = FileUtils.getPath(args[0]);
            String folderOutPath = Paths.get(folderPath, OUTPUT_FOLDER).toString();

//          checking if input folder is correct and all data files are in place
            InputValidator.validate(folderPath);

//          creating output folder, its under <inputFolder>/out
            FileUtils.createFolder(folderOutPath);

//          settung up logger to write Errors (SEVERE level) also in errors.log file under output Folder
            LoggerConfiguration.init(folderOutPath);

            greetings(folderPath, folderOutPath);

//          data processing and reporting
            DataProcessor.processData(folderPath, folderOutPath);

        } catch (ProcessingException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getCause());
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to initialize logger");
        }
    }

    private static void greetings(String folderPath, String folderOutPath) {
//      Using here System.out.println instead of Logger because Console logger was removed from configuration.
//      see LoggerConfiguration#34
//      Logger write only errors (SEVERE level) to errors.log file

        Date time = new Date();

        System.out.println(" ---------------------------------------------------------------------------------" );
        System.out.println(" Hi everybody !!!");
        System.out.println(" Hope you will enjoy using this tool\n");
        System.out.println(" - Data files will be taken from: \n     " + folderPath);
        System.out.println(" - Report files will be stored under: \n     " + folderOutPath);
        System.out.println(" - Errors (if it is) will see show below and stored to file : \n     " + folderOutPath + "/errors.log");
        System.out.println(" ---------------------------------------------------------------------------------" );
        System.out.println(" \n Finished in "  + (new Date().getTime() - time.getTime()) + "ms");

    }

}










