package com.tw.conference.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tw.conference.Configuration;
import com.tw.conference.util.TimeUtils;

/**
 * This class represents a session in the conference. 
 * Each session contains multiple talks.
 * By default, morning sessions begin at 9am and must finish by 12 noon, for lunch 
 * and afternoon sessions begin at 1pm and must finish in time for the networking event. 
 * You can change the quote parameters above in application-[ENVIRONMENT].properties file.
 * 
 * @author leone
 * @since  1.0.0
 */
public class Session implements Serializable {
    
	private static final long serialVersionUID = 2777910400018854205L;
	
	private List<Talk> talks;
    private int remainingDuration;
    private int startTime;
    private Session networking;

    public Session(int duration, int startTime) {
    	this.setRemainingDuration(duration);
    	this.setStartTime(startTime);
    	this.setTalks(new ArrayList<>());
    }

    /**
     * Allocates a list of talks if the session still has available time.
     * 
     * @param talks
     */
    public void allocate(List<Talk> talks) {
    	for (Iterator<Talk> iterator = talks.iterator(); iterator.hasNext();) {
    		Talk talk = iterator.next();
    		if(this.allocate(talk)) {
    			iterator.remove();	
    		}else {
    			break;
    		}
    	}
    }
    
    /**
     * Allocates a talk if the session still has available time.
     * 
     * @param talk
     */
    public boolean allocate(Talk talk) {
		if (this.hasTimeAvailable(talk) && this.addTalk(talk)) {
			this.setRemainingDuration(this.getRemainingDuration() - talk.getDuration());
			return true;
		}
		return false;
    }
    
    private boolean addTalk(Talk talk) {
        return this.getTalks().add(talk);
    }

    public boolean hasTimeAvailable(Talk talk) {
        /*
         * Trying evolving coding practice.
         * return this.getRemainingDuration() >= talk.getDuration() || (this.getLastTalkSessionStartTime() < Configuration.getLunchSessionStartTime());
         */
        return this.getRemainingDuration() >= talk.getDuration();
    }
    
    /**
     * Returns the last session start talk of a talk.
     * @return
     */
    public int getLastTalkSessionStartTime() {
    	int nextTalkStartTime = this.getStartTime();
    	nextTalkStartTime += this.getTalks().stream().mapToInt(o->o.getDuration()).sum();
	    return nextTalkStartTime;
    }
    
    /**
     * Adds talks to the StringBuilder with their starting time and their duration and increments the next talk start time.
     * 
     * @param talks
     * @param startTime
     * @param sb
     * @return the next talk start time
     */
    private int appendTalk(List<Talk> talks, int startTime, StringBuilder sb) {
        int nextTalkStartTime = startTime;
        for (Talk talk : talks) {
            sb.append(TimeUtils.minutesToDisplayTime(nextTalkStartTime));
            sb.append(" ");
            sb.append(talk);
            sb.append(Configuration.BREAK_LINE);
            nextTalkStartTime += talk.getDuration();
        }
        return nextTalkStartTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendTalk(this.getTalks(), this.getStartTime(), sb);
        if (this.getNetworking() != null) {
            appendTalk(this.getNetworking().getTalks(), this.getNetworking().getStartTime(), sb);
        }
        return sb.toString();
    }
    
    //getters and setters

	public List<Talk> getTalks() {
		return this.talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

	public int getRemainingDuration() {
		return this.remainingDuration;
	}

	public void setRemainingDuration(int remainingDuration) {
		this.remainingDuration = remainingDuration;
	}

	public int getStartTime() {
		return this.startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public Session getNetworking() {
		return this.networking;
	}

	public void setNetworking(Session networking) {
		this.networking = networking;
	}
}
