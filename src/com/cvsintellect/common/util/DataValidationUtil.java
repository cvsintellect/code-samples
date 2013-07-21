package com.cvsintellect.common.util;

import java.util.List;

import com.google.appengine.api.datastore.Text;

public class DataValidationUtil {
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty())
			return true;
		return false;
	}

	public static boolean isEmpty(Text txt) {
		if (txt == null || isEmpty(txt.getValue()))
			return true;
		return false;
	}

	public static boolean isEmpty(List<?> entries) {
		if (entries == null || entries.isEmpty())
			return true;
		return false;
	}
}
