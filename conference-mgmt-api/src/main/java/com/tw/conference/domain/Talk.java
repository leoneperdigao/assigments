package com.tw.conference.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tw.conference.Configuration;
import com.tw.conference.enums.DurationType;

/**
 * This class represents a talk in the conference. 
 * All talk durations are either in minutes (not hours) or lightning (5 minutes).
 * 
 * @author leone
 * @since  1.0.0
 */
public class Talk implements Serializable {

	private static final long serialVersionUID = 4105350720872864641L;
	private static final Logger LOG = LogManager.getLogger(Talk.class);
	
	private String title;
	private int duration;
	private DurationType type;

	public Talk(String title, int duration, DurationType type) {
		this.title = title;
		this.duration = duration;
		this.type = type;
	}
	
	public static List<Talk> parse(BufferedReader input) throws IOException {
		List<Talk> talks = new ArrayList<Talk>();
		for (String line; (line = input.readLine()) != null;) {
			line = line.trim();
			Talk talk = Talk.parse(line);
			if (talk == null) {
				continue;
			}
			if(!talks.contains(talk)) {
				talks.add(talk);
			}else {
				LOG.warn("Drooping-out repeated talk. " + talk.getTitle());
			}
		}
		return talks;
	}
	
	public static Talk parse(String line) {
		if (line.length() == 0) {
			return null;
		}
		
		Matcher match = Configuration.getInputLinePattern().matcher(line);
		if (!match.find()) {
			LOG.error("Invalid pattern string: " + line);
			return null;
		}

		String title = match.group(Configuration.getTalkTitleGroup());
		int duration = parseDuration(match.group(Configuration.getTalkDurationGroup()));
		DurationType unit = DurationType.parse(match.group(Configuration.getTalkDurationUnitGroup()));
		
		Talk talk = new Talk(title, duration, unit);
		if (talk.getDuration() > Configuration.getMaxSessionDuration()) {
			LOG.warn("Duration of talk '" + title + "' is more than the maximum duration" + " allowed for a session. Talk will be dropped-out.");
			return null;
		}

		return talk;
	}
	
	private static int parseDuration(String durationInString) {
		if (durationInString == null) {
			durationInString = "-1";
		}
		return Integer.parseInt(durationInString);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDuration() {
		return this.getType().toMinutes(duration);
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public DurationType getType() {
		return type;
	}

	public void setType(DurationType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		if(this.getTitle().equals(Configuration.getLunchSessionTitle()) || this.getTitle().equals(Configuration.getNetworkingEventTitle())) {
			return title;
		}else if(type.equals(DurationType.MINUTES)) {
			return title + " " + duration + type;			
		}
		return title + " " + type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Talk other = (Talk) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
