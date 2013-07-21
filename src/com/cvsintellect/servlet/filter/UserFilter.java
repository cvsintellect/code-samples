package com.cvsintellect.servlet.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cvsintellect.db.cache.CacheService;
import com.cvsintellect.db.dao.UserDataDAO;
import com.cvsintellect.db.model.UserDTO;
import com.cvsintellect.servlet.constants.KeyConstants;
import com.cvsintellect.servlet.constants.PageConstants;
import com.google.appengine.api.datastore.Key;
import com.ji.rm.servlet.util.GAELogger;

public final class UserFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(UserFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		GAELogger.logInfo(LOG, UserFilter.class.getName());

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession();

		// create user if doesn't exist
		if (session.getAttribute(KeyConstants.USER_ID.toString()) == null) {
			try {
				UserDataDAO userDataDAO = new UserDataDAO(new CacheService());
				String emailId = "test@test.com";
				Key userKey = null;
				if (userDataDAO.hasUserByEmailId(emailId)) {
					UserDTO userDTO = userDataDAO.getUserByEmailId(emailId);
					userKey = userDTO.getKey();
				}
				else {
					userKey = userDataDAO.createNewUser(emailId);
				}
				session.setAttribute(KeyConstants.USER_ID.toString(), userKey);
			}
			catch (Exception e) {
				httpResponse.sendRedirect(PageConstants.ERROR_URL);
			}
		}

		filterChain.doFilter(request, response);
	}
}
