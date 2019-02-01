package com.tw.conference;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import org.junit.Test;

import com.tw.conference.domain.Conference;
import com.tw.conference.util.FileUtils;

public class ConferenceTest {
	
	private static final String FEEL_TALKS_INPUT_FILE = "/few_talks_input_file";
	private static final String MORE_TALKS_INPUT_FILE = "/more_talks_input_file";
	private static final String REPEATED_TALKS_INPUT_FILE = "/repeated_talks_input_file";
	private static final String SHY_CONFERENCE_INPUT_FILE = "/shy_conference_input_file";
	
    @Test
    public void fewTalksTest() throws IOException {
    	assertTrue(genericTest(FEEL_TALKS_INPUT_FILE));
    }
    
    @Test
    public void moreTalksTest() throws IOException {
    	assertTrue(genericTest(MORE_TALKS_INPUT_FILE));
    }
    
    @Test
    public void repeatedTalksTest() throws IOException {
    	assertTrue(genericTest(REPEATED_TALKS_INPUT_FILE));
    }
    
    @Test
    public void shyConferenceTest() throws IOException {
    	assertTrue(genericTest(SHY_CONFERENCE_INPUT_FILE));
    }

    private boolean genericTest(String inputFile) throws IOException {
    	BufferedReader input = FileUtils.getBufferedReader(inputFile, this);
    	Conference conference = new Conference(input);
		
    	BufferedReader output = FileUtils.getBufferedReader(getExpectedOutputFile(inputFile), this);
    	String expected = output.lines().collect(Collectors.joining(Configuration.BREAK_LINE));
    	expected += Configuration.BREAK_LINE;
    	
    	return conference.toString().equals(expected);
    }

    private String getExpectedOutputFile(String inputFile) {
    	return inputFile + "_expected";
    }
}