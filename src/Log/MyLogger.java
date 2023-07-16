package Log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
private static final Logger logger = Logger.getLogger(MyLogger.class.getName());

    public MyLogger() {
        logger.setLevel(Level.ALL);
    }


    public void logToFile(String logMsg,String fileName){
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler(fileName,true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.setLevel(Level.ALL);
            logger.log(Level.ALL,logMsg);
            fileHandler.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void logInfo(String message){
        logger.info(message);
    }

    public void logWarning(String message){
        logger.warning(message);
    }

    public void logError(String message,Throwable throwable){
        logger.log(Level.SEVERE,message,throwable);
    }
}
