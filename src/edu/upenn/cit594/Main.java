package edu.upenn.cit594;
import edu.upenn.cit594.datamanagement.DataReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UI;
import edu.upenn.cit594.util.StateData;
import edu.upenn.cit594.util.Tweet;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {return;}

        Logger thisLogger = Logger.getInstance();

        thisLogger.changeDest(args[2]);

        Processor processor = new Processor(new DataReader(args[0],args[1]));

        UI ui = new UI(processor);

        ui.printFluTweets();

    }
}
