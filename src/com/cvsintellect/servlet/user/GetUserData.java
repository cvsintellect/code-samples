package com.cvsintellect.servlet.user;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cvsintellect.db.cache.CacheService;
import com.cvsintellect.db.dao.AbstractDAO;
import com.cvsintellect.db.dao.UserDataDAO;
import com.cvsintellect.db.model.EntryDTO;
import com.cvsintellect.db.model.UserDTO;
import com.cvsintellect.servlet.constants.KeyConstants;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import com.ji.rm.servlet.util.AjaxResponse;
import com.ji.rm.servlet.util.GAELogger;

public class GetUserData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(GetUserData.class.getName());

	public GetUserData() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		Key userKey = (Key) session.getAttribute(KeyConstants.USER_ID.toString());

		try {
			UserDTO userData = new UserDataDAO(new CacheService()).getUserDataByKey(userKey);
			AbstractDAO.unlinkUser(userData);
			List<EntryDTO> entries = userData.getEntries();

			AjaxResponse.writeOutput(response, new Gson().toJson(entries), "application/json");
			return;
		}
		catch (Exception e) {
			GAELogger.logError(LOG, e);
		}

		AjaxResponse.writeFailureOutput(response, "Error Occurred!");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
