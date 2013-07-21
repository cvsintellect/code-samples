package com.cvsintellect.servlet.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.ji.rm.servlet.util.GAELogger;

public final class FileFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(FileFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		GAELogger.logInfo(LOG, FileFilter.class.getName());
		
		// create user if doesn't exist

		filterChain.doFilter(request, response);
	}

}
