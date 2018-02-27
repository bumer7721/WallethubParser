package com.ef.runner.enums;

public enum Duration {
	
	HOURLY("hourly", 1)
	, DAILY("daily", 24)
	;
	
	private String label;
	private Integer hours;
	
	private Duration(String label, Integer hours) {
		this.label = label;
		this.hours = hours;
	}
	
	public String getLabel() {
		return label;
	}

	public Integer getHours() {
		return hours;
	}

	public static Duration valueOfLabel(String label) {
		for (Duration d : Duration.values()) {
			if (d.getLabel().equals(label)) {
				return d;
			}
		}
		return null;
	}
}
