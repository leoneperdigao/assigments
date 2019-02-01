package com.tw.conference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tw.conference.domain.Conference;

/**
 * This class represents a main Conference Application.
 * 
 * @author leone
 * @since  1.0.0
 */
public class ConferenceApplication {
	
	private static final Logger LOG = LogManager.getLogger(ConferenceApplication.class);
	
    public static void main(String[] args) {

        if (args.length < 1) {
        	LOG.error("You must pass a file path as input.");
            System.exit(1);
        }

        File inputFile = new File(args[0]);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            Conference conference = new Conference(reader);
            System.out.print(conference.toString());
        } catch (IOException e) {
        	LOG.fatal("Cannot read from input file: " + inputFile.getAbsolutePath());
            System.exit(1);
        }
    }
}
