package com.tw.conference.enums;

/**
 * Enumeration to define duration types for talks.
 *  <li>{@link #MINUTES}</li>
 *  <li>{@link #LIGHTENING}</li>
 *  <li>{@link #OPEN}</li>
 *  
 * @author leone
 *
 */
public enum DurationType {
	
	MINUTES("min", 1), 
	LIGHTENING("lightning", 5),
	OPEN("", 0);

	private String label;
	private int multiplier;

	private DurationType(String label, int multiplier) {
		this.label = label;
		this.multiplier = multiplier;
	}

	public int toMinutes(int duration) {
		return (multiplier * duration);
	}
	
	public static DurationType parse(String description) {
		DurationType unit = null;
		switch (description) {
			case "min":
				unit = DurationType.MINUTES;
				break;
			case "lightning":
				unit = DurationType.LIGHTENING;
				break;
			case "":
				unit = DurationType.OPEN;
				break;
			default:
				break;
		}
		return unit;
	}

	@Override
	public String toString() {
		return label;
	}
}
