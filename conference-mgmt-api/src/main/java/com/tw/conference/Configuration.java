package com.tw.conference;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tw.conference.enums.DurationType;

/**
 * Util class to load properties and define constants.
 * 
 * @author leone
 * @since  1.0.0
 */
public final class Configuration {
	
	private static final Logger LOG = LogManager.getLogger(Configuration.class);
	
	public static final String BREAK_LINE = System.getProperty("line.separator");
	public static final DurationType NETWORKING_DURATION_UNIT = DurationType.OPEN;
	public static final DurationType LUNCH_DURATION_UNIT = DurationType.MINUTES;
	
	private static final String INPUT_LINE_PATTERN = "input.line.pattern";
    private static final String TALK_TITLE_GROUP = "talk.title.group";
    private static final String TALK_DURATION_GROUP = "talk.duration.group";
    private static final String TALK_DURATION_UNIT_GROUP = "talk.duration.unit.group";
    
    private static final String MORNING_SESSION_START_TIME = "morning.session.start.time";
    private static final String MORNING_SESSION_DURATION = "morning.session.duration";
    
    private static final String LUNCH_SESSION_TITLE = "lunch.session.title";
    private static final String LUNCH_SESSION_START_TIME = "lunch.session.start.time";
    private static final String LUNCH_SESSION_DURATION = "lunch.session.duration";
    
    private static final String AFTERNOON_SESSION_START_TIME = "afternoon.session.start.time";
    private static final String AFTERNOON_SESSION_MAX_DURATION = "afternoon.session.duration";
    
    private static final String NETWORKING_EVENT_TITLE = "networking.event.title";
    private static final String NETWORKING_EVENT_DURATION = "networking.event.duration";
    private static final String NETWORKING_EVENT_MIN_START_TIME = "networking.event.min.start.time";
    private static final String NETWORKING_EVENT_MAX_START_TIME = "networking.event.max.start.time";
    
    private static Properties props;

    private Configuration() {}
    
	private static String getPropertieValue(String key) {
		if(props == null) {
			props = new Properties();
			try (InputStream input = ClassLoader.getSystemResourceAsStream("application.properties")){  
	    		props.load(input);
	    	} catch (IOException ex) {
	    		LOG.error("Error trying to load application.properties file.", ex);
	    	}
		}
		return props.getProperty(key);
    }
    
    public static Pattern getInputLinePattern() {
    	return Pattern.compile(getPropertieValue(INPUT_LINE_PATTERN));
    }
    
    public static int getTalkTitleGroup() {
    	return Integer.valueOf(getPropertieValue(TALK_TITLE_GROUP));
    }
    
    public static int getTalkDurationGroup() {
    	return Integer.valueOf(getPropertieValue(TALK_DURATION_GROUP));
    }
    
    public static int getTalkDurationUnitGroup(){
    	return Integer.valueOf(getPropertieValue(TALK_DURATION_UNIT_GROUP));
    }
    
    public static int getMorningSessionStartTime() {
    	return Integer.valueOf(getPropertieValue(MORNING_SESSION_START_TIME));
    }
    
    public static int getMorningSessionDuration() {
    	return Integer.valueOf(getPropertieValue(MORNING_SESSION_DURATION));
    }
    
    public static String getLunchSessionTitle() {
    	return getPropertieValue(LUNCH_SESSION_TITLE);
    }
    
    public static int getLunchSessionStartTime() {
    	return Integer.valueOf(getPropertieValue(LUNCH_SESSION_START_TIME));
    }
    
    public static int getLunchSessionDuration() {
    	return Integer.valueOf(getPropertieValue(LUNCH_SESSION_DURATION));
    }
    
    public static int getAfternoonStartTime() {
    	return Integer.valueOf(getPropertieValue(AFTERNOON_SESSION_START_TIME));
    }
    
    public static int getAfternoonMaxDuration() {
    	return Integer.valueOf(getPropertieValue(AFTERNOON_SESSION_MAX_DURATION));
    }
    
    public static String getNetworkingEventTitle() {
    	return getPropertieValue(NETWORKING_EVENT_TITLE);
    }
    
    public static int getNetworkingEventDuration() {
    	return Integer.valueOf(getPropertieValue(NETWORKING_EVENT_DURATION));
    }
    
    public static int getNetworkingMinStartTime() {
    	return Integer.valueOf(getPropertieValue(NETWORKING_EVENT_MIN_START_TIME));
    }
    
    public static int getNetworkingMaxStartTime() {
    	return Integer.valueOf(getPropertieValue(NETWORKING_EVENT_MAX_START_TIME));
    }
    
    public static int getMaxSessionDuration() {
    	return Collections.max(Arrays.asList(getMorningSessionDuration(), getLunchSessionDuration(), getAfternoonMaxDuration()));
    }
}
