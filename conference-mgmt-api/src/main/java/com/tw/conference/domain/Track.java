package com.tw.conference.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a track for the conference. 
 * The conference has multiple tracks each of which has a morning and afternoon session.
 * 
 * @author leone
 * @since  1.0.0
 */
public class Track implements Serializable {
	
	private static final long serialVersionUID = -5376426055430058728L;
	private List<Session> sessions;

    public Track() {
    	setSessions(new ArrayList<>());
    }

    public boolean addSession(Session session) {
        return this.getSessions().add(session);
    }

    public List<Session> getSessions() {
		return this.sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Session session : this.getSessions()) {
            sb.append(session);
        }
        return sb.toString();
    }
}
