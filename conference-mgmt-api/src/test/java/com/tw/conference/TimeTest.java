package com.tw.conference;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tw.conference.util.Time;

public class TimeTest {
	
    @Test
    public void testMinutesToDisplayTime() {
        assertEquals("01:00AM", Time.minutesToDisplayTime(1 * 60));
        assertEquals("12:00AM", Time.minutesToDisplayTime(0));
        assertEquals("12:00PM", Time.minutesToDisplayTime(12 * 60));
        assertEquals("01:00PM", Time.minutesToDisplayTime(13 * 60));
        assertEquals("11:59PM", Time.minutesToDisplayTime(12 * 60 + 11 * 60 + 59));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMinutesToDisplayTimeBoundary() {
        assertEquals("12:00AM", Time.minutesToDisplayTime(12 * 60 + 12 * 60));
    }
}
