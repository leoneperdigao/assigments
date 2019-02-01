package com.tw.conference.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tw.conference.Configuration;
import com.tw.conference.enums.DurationType;

/**
 * This class represents a Conference. 
 * The conference has multiple tracks each of which has a morning and afternoon session.
 * Each session contains multiple talks.
 * 
 * @author leone
 * @since  1.0.0
 */
public class Conference implements Serializable {
	
	private static final long serialVersionUID = -4476638798679831175L;
    
	private List<Track> tracks;
	private int lastTalkOfDayStartTime;

    public Conference (BufferedReader input) throws IOException {
		List<Talk> talks = Talk.parse(input);
		this.setTracks(new ArrayList<>());
		while (talks.size() != 0) {
			Track track = new Track();
			track.addSession(createMorningSession(talks));
			track.addSession(createLunchSession());
			
			Session afternoon = createAfternoonSession(talks);
			if(afternoon.getLastTalkSessionStartTime() > getLastTalkOfDayStartTime()) {
				setLastTalkOfDayStartTime(afternoon.getLastTalkSessionStartTime());
			}
			
			track.addSession(afternoon);
			this.addTrack(track);
		}
		adjustNetworkingStartEvent();
	}
    
    /**
     * Create morning session based on configuration and list of talks
     * 
     * @param talks
     * @return session instance
     */
    private Session createMorningSession(List<Talk> talks) {
    	Session morning = new Session(Configuration.getMorningSessionDuration(), Configuration.getMorningSessionStartTime());
		morning.allocate(talks);
		return morning;
    }
    
    /**
     * Create morning session based on configuration
     * 
     * @return session instance
     */
    private Session createLunchSession() {
    	Session lunch = new Session(Configuration.getLunchSessionDuration(), Configuration.getLunchSessionStartTime());
		if(!lunch.allocate(new Talk(Configuration.getLunchSessionTitle(), Configuration.getLunchSessionDuration(), DurationType.MINUTES))) {
			throw new IllegalStateException("There is no remaining time available.");
		}
		return lunch;
    }
    
    /**
     * Create a afternoon session based on configuration parameters and list of talks.
     * In the afternoon session will be appended the networking session.
     * 
     * @param talks
     * @return session instance
     */
    private Session createAfternoonSession(List<Talk> talks) {
    	Session afternoon = new Session(Configuration.getAfternoonMaxDuration(), Configuration.getAfternoonStartTime());
    	afternoon = createNetworkingSession(afternoon);
		afternoon.allocate(talks);
		return afternoon;
    }
    
    /**
     * Create a networking session based on configuration parameters and session.
     * 
     * @param session
     * @return session instance
     */
    private Session createNetworkingSession(Session session) {
    	Talk networkingEvent = new Talk(Configuration.getNetworkingEventTitle(), Configuration.getNetworkingEventDuration(), DurationType.OPEN);
		Session networkingSession = new Session(networkingEvent.getDuration(), Configuration.getNetworkingMinStartTime());
		if(!networkingSession.allocate(networkingEvent)) {
			throw new IllegalStateException("There is no remaining time available.");
		}
		session.setNetworking(networkingSession);
		return session;
    }
    
    private void addTrack(Track track) {
        this.getTracks().add(track);
    }
    
    /**
     * Adjust networking start time by getting the last talk start time of day
     * and updating the networking event inside the session.
     */
    private void adjustNetworkingStartEvent() {
    	for(Track track : this.getTracks()) {
    		for(Session session : track.getSessions()) {
    			if(session.getNetworking() != null) {
    				session.getNetworking().setStartTime(getLastTalkOfDayStartTime());
    			}
    		}
    	}
    }
    
	@Override
    public String toString() {
    	StringBuilder str = new StringBuilder();
    	int trackNumber = 1;
    	for (Iterator<Track> iterator = tracks.iterator(); iterator.hasNext();) {
    		str.append("Track " + trackNumber + ":" + Configuration.BREAK_LINE);
    		str.append((Track) iterator.next());
    		if(iterator.hasNext()) {
    			str.append(Configuration.BREAK_LINE);
    		}
    		trackNumber++;
    	}
    	return str.toString();
    }

	//getters and setters

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	public int getLastTalkOfDayStartTime() {
		return lastTalkOfDayStartTime;
	}

	public void setLastTalkOfDayStartTime(int lastTalkOfDayStartTime) {
		this.lastTalkOfDayStartTime = lastTalkOfDayStartTime;
	}
	
}
