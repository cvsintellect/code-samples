package com.cvsintellect.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cvsintellect.servlet.constants.PageConstants;

@SuppressWarnings("serial")
public class Welcome extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.sendRedirect(PageConstants.PROFILE_URL);
	}
}
