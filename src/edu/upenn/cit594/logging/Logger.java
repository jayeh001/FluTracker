package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    String fileName;

    private Logger() {
        //this.fileName = fileName;
    }
    private static final Logger logger = new Logger(); //TODO: idk wtf is going on here but fix it
    public static Logger getInstance() { return logger;}
    public void logEvent(String state, String tweet) {
        try {
            FileWriter fw = new FileWriter(fileName,true);
            String event = state + "\t" + tweet + "\n";
            fw.write(event);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeDest(String fileName) {
        this.fileName = fileName;
    }

}
