package com.ef.runner.enums;

public enum ParameterAlias {
	
	ACCESS_LOG("--accesslog")
	, START_DATE("--startDate")
	, DURATION("--duration")
	, THRESHOLD("--threshold")
	;
	
	private String alias;
	
	private ParameterAlias(String alias) {
		this.alias = alias;
	}
	
	public String getAlias() {
		return alias;
	}

	public static ParameterAlias valueOfAlias(String alias) {
		for (ParameterAlias a : ParameterAlias.values()) {
			if (a.getAlias().equals(alias)) {
				return a;
			}
		}
		return null;
	}
}
