package com.cvsintellect.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cvsintellect.db.cache.CacheService;
import com.cvsintellect.db.dao.EntryDAO;
import com.cvsintellect.db.dao.UserDataDAO;
import com.cvsintellect.db.model.AbstractDTO;
import com.cvsintellect.db.model.UserDTO;
import com.cvsintellect.servlet.constants.KeyConstants;
import com.cvsintellect.servlet.constants.MessageConstants;
import com.cvsintellect.servlet.constants.ResourceConstants;
import com.cvsintellect.servlet.util.AjaxResponse;
import com.google.appengine.api.datastore.Key;

public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Delete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		Key userId = (Key) session.getAttribute(KeyConstants.USER_ID.toString());

		String resource = request.getPathInfo();

		try {
			UserDTO userData = new UserDataDAO(new CacheService()).getUserDataByKey(userId);

			if (resource.equals(ResourceConstants.ENTRY)) {
				String keyDigest = request.getParameter("entry[keyDigest]");

				int index = AbstractDTO.getIndexOf(userData.getEntries(), keyDigest);

				if (index == -1) {
					AjaxResponse.writeFailureOutput(response, MessageConstants.CANNOT_FIND_OBJECT_AT_INDEX);
					return;
				}

				new EntryDAO(new CacheService()).deleteEntry(userId, index);

				AjaxResponse.writeSuccessOutput(response);
				return;
			}
		}
		catch (Exception e) {
			AjaxResponse.writeFailureOutput(response, MessageConstants.GENERIC_ERROR_MESSAGE);
			return;
		}
	}
}
