package com.cvsintellect.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cvsintellect.db.cache.CacheService;
import com.cvsintellect.db.dao.UserDataDAO;
import com.cvsintellect.db.model.UserDTO;
import com.cvsintellect.servlet.constants.KeyConstants;
import com.cvsintellect.servlet.constants.PageConstants;
import com.cvsintellect.servlet.constants.ResourceConstants;
import com.google.appengine.api.datastore.Key;

public class Read extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Read() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		Key userId = (Key) session.getAttribute(KeyConstants.USER_ID.toString());

		String resource = request.getPathInfo();

		try {
			UserDTO userData = new UserDataDAO(new CacheService()).getUserDataByKey(userId);

			request.setAttribute(KeyConstants.USER_DATA.toString(), userData);

			if (resource.equals(ResourceConstants.PROFILE)) {
				getServletContext().getRequestDispatcher(PageConstants.PROFILE_PAGE).forward(request, response);
				return;
			}
		}
		catch (Exception e) {
			response.sendRedirect(PageConstants.ERROR_URL);
			return;
		}

		response.sendRedirect(PageConstants.ERROR_URL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
