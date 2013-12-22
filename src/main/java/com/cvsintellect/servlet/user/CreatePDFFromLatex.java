package com.cvsintellect.servlet.user;

import com.cvsintellect.service.LatexService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreatePDFFromLatex extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		getServletContext().getRequestDispatcher("/jsp/latextopdf.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String texFile = request.getParameter("texFile");
		String compiler = request.getParameter("compiler");

		try {
			String pdfLink = LatexService.getPDFLink(texFile, compiler);

			response.sendRedirect(pdfLink);
			return;
		}
		catch (Exception e) {
			//
		}

		response.sendRedirect("/");
	}
}
