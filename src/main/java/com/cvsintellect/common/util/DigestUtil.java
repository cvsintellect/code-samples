package com.cvsintellect.common.util;

import org.apache.commons.codec.digest.DigestUtils;

public class DigestUtil {
	public static String getDigestFor(String input) {
		if (!DataValidationUtil.isEmpty(input)) {
			return DigestUtils.shaHex(input);
		}
		return null;
	}

	public static boolean stringEqualsDigest(String input, String digest) {
		if (!DataValidationUtil.isEmpty(input) && !DataValidationUtil.isEmpty(digest)) {
			return getDigestFor(input).equals(digest);
		}
		return false;
	}
}
