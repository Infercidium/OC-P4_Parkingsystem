package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * App class containing the main method to start the application
 */
public class App {
    private static final Logger logger = LogManager.getLogger("App");

    /**
     * Main method initializing the application
     */
    public static void main(String[] args){
        logger.info("Initializing Parking System");
        InteractiveShell.loadInterface();

    }
}
