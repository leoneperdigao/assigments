package com.tw.conference;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tw.conference.util.TimeUtils;

public class TimeTest {
	
    @Test
    public void testMinutesToDisplayTime() {
        assertEquals("01:00AM", TimeUtils.minutesToDisplayTime(1 * 60));
        assertEquals("12:00AM", TimeUtils.minutesToDisplayTime(0));
        assertEquals("12:00PM", TimeUtils.minutesToDisplayTime(12 * 60));
        assertEquals("01:00PM", TimeUtils.minutesToDisplayTime(13 * 60));
        assertEquals("11:59PM", TimeUtils.minutesToDisplayTime(12 * 60 + 11 * 60 + 59));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMinutesToDisplayTimeBoundary() {
        assertEquals("12:00AM", TimeUtils.minutesToDisplayTime(12 * 60 + 12 * 60));
    }
}
