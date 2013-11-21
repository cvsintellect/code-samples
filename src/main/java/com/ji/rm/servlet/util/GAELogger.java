package com.ji.rm.servlet.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class GAELogger {
	public static void logInfo(Logger LOG, String message) {
		LOG.info(message);
	}

	public static void logError(Logger LOG, Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		LOG.severe(sw.toString());
	}
}
