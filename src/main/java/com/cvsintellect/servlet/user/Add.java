package com.cvsintellect.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cvsintellect.db.cache.CacheService;
import com.cvsintellect.db.dao.EntryDAO;
import com.cvsintellect.db.model.EntryDTO;
import com.cvsintellect.servlet.constants.KeyConstants;
import com.cvsintellect.servlet.constants.MessageConstants;
import com.cvsintellect.servlet.constants.ResourceConstants;
import com.cvsintellect.servlet.util.AjaxResponse;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Add() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		Key userId = (Key) session.getAttribute(KeyConstants.USER_ID.toString());

		String resource = request.getPathInfo();

		try {
			if (resource.equals(ResourceConstants.ENTRY)) {
				String entryText = request.getParameter("entry[text]");

				String keyDigest = new EntryDAO(new CacheService()).addNewEntry(userId, new EntryDTO(new Text(entryText)));

				AjaxResponse.writeOutput(response, keyDigest);
				return;
			}
		}
		catch (Exception e) {
			AjaxResponse.writeFailureOutput(response, MessageConstants.GENERIC_ERROR_MESSAGE);
			return;
		}
	}
}
