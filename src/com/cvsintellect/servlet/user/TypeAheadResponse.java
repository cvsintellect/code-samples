package com.cvsintellect.servlet.user;

import java.util.ArrayList;
import java.util.List;

public class TypeAheadResponse {
	private List<String> options;

	public TypeAheadResponse() {
		super();
		this.options = new ArrayList<String>();
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public void addOption(String option) {
		options.add(option);
	}
}
