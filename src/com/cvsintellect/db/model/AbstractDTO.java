package com.cvsintellect.db.model;

import java.util.List;

import com.cvsintellect.common.util.DataValidationUtil;
import com.google.appengine.api.datastore.Key;


public abstract class AbstractDTO {
	public abstract Key getKey();

	public boolean equals(Object that) {
		if (that instanceof AbstractDTO && this.getKey() != null && ((AbstractDTO) that).getKey() != null) {
			return this.getKey().equals(((AbstractDTO) that).getKey());
		}
		return false;
	}

	public static int getIndexOf(List<? extends KeyDigestSearchable> list, String keyDigest) {
		if (list == null || DataValidationUtil.isEmpty(keyDigest))
			return -1;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKeyDigest().equals(keyDigest)) {
				return i;
			}
		}
		return -1;
	}
}
