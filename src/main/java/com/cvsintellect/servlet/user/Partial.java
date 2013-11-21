package com.cvsintellect.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cvsintellect.servlet.constants.PageConstants;
import com.cvsintellect.servlet.constants.ResourceConstants;

public class Partial extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Partial() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resource = request.getPathInfo();

		try {
			if (resource.equals(ResourceConstants.PROFILE)) {
				getServletContext().getRequestDispatcher(PageConstants.ENTRY_PARTIAL_PAGE).forward(request, response);
				return;
			}
		}
		catch (Exception e) {
			response.sendRedirect(PageConstants.ERROR_URL);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
