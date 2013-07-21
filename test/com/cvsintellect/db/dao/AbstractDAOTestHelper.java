package com.cvsintellect.db.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;

import com.cvsintellect.db.cache.CacheService;
import com.cvsintellect.db.model.UserDTO;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public abstract class AbstractDAOTestHelper {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	protected CacheService getMockedCacheService() {
		CacheService cacheService = mock(CacheService.class);
		when(cacheService.getUser(any(Key.class))).thenReturn(null);

		doNothing().when(cacheService).putUser(any(UserDTO.class));

		doNothing().when(cacheService).removeUser(any(Key.class));
		return cacheService;
	}

	protected HttpServletResponse getMockedResponse() throws IOException {
		HttpServletResponse response = mock(HttpServletResponse.class);
		doNothing().when(response).setStatus(anyInt());
		doNothing().when(response).setContentType(anyString());
		PrintWriter printWriter = mock(PrintWriter.class);
		doNothing().when(printWriter).print(anyString());
		doNothing().when(printWriter).flush();
		doNothing().when(printWriter).close();
		when(response.getWriter()).thenReturn(printWriter);
		return response;
	}
}
