package com.scribtex.model;

import java.util.ArrayList;

public class ClsiInputResourceList extends ArrayList<ClsiInputResource> {

	private static final long serialVersionUID = 5737244738929177523L;

	private int primaryResource = 0;

	public ClsiInputResourceList() {
		super();
	}

	public int getPrimaryResource() {
		if (primaryResource >= this.size() || primaryResource < 0) {
			return 0;
		}
		return primaryResource;
	}

	public void setPrimaryResource(int primaryResource) {
		this.primaryResource = primaryResource;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void add(ClsiInputResource resource, boolean isPrimary) {
		this.add(resource);
		if (isPrimary) {
			this.setPrimaryResource(this.size() - 1);
		}
	}

}
