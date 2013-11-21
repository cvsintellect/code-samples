package com.cvsintellect.servlet.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class AjaxResponse {
	public static void writeFailureOutput(HttpServletResponse response, String message) throws IOException {
		writeFailureOutput(response, message, 450);
	}

	public static void writeFailureOutput(HttpServletResponse response, String message, int responseCode) throws IOException {
		response.setStatus(responseCode);
		writeOutput(response, message);
	}

	public static void writeOutput(HttpServletResponse response, String message, String mimeType) throws IOException {
		response.setContentType(mimeType);
		writeOutput(response, message);
	}

	public static void writeSuccessOutput(HttpServletResponse response) throws IOException {
		writeOutput(response, "OK");
	}

	public static void writeOutput(HttpServletResponse response, String message) throws IOException {
		PrintWriter out = response.getWriter();
		out.print(message);
		out.flush();
		out.close();
	}
}
