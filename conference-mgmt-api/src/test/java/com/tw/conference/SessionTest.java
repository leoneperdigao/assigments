/**
 * 
 */
package com.tw.conference;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tw.conference.domain.Session;
import com.tw.conference.domain.Talk;
import com.tw.conference.enums.DurationType;

/**
 * @author leone
 *
 */
public class SessionTest {
	
	private static final int MAX_MORNING_SESSION_DURATION = 60*7;
	private List<Talk> talks = new ArrayList<>();
	
	@Before
	public void setup() {
		talks = new ArrayList<>();
		this.talks.add(new Talk("Talk 1", 60, DurationType.MINUTES));
		this.talks.add(new Talk("Talk 2", 60, DurationType.MINUTES));
		this.talks.add(new Talk("Talk 3", 50, DurationType.MINUTES));
		this.talks.add(new Talk("Talk 4", 15, DurationType.LIGHTENING));
	}
	
	@Test
	public void timeAvailableTest() {
		Session session = new Session(MAX_MORNING_SESSION_DURATION, Configuration.getMorningSessionStartTime());
		session.allocate(talks);
		assertEquals(4, session.getTalks().size());
	}
	
	/*
	@Test
	public void allocateFailTest() {
		Session session = new Session(Configuration.getMorningSessionDuration(), Configuration.getMorningSessionStartTime());
		session.allocate(talks);
		assertEquals(4, session.getTalks().size());
	}*/

}
