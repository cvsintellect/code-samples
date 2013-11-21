package com.cvsintellect.db.dao;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.cvsintellect.db.cache.CacheService;
import com.cvsintellect.db.dao.migrator.UserDataMigrator;
import com.cvsintellect.db.model.EntryDTO;
import com.cvsintellect.db.model.UserDTO;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

public class EntryDAOTest extends AbstractDAOTestHelper {
	private static final String EMAIL_ID = "first.lastname@test.com";
	private static final String SUMMARY = "summary";

	@Test
	public void shouldAddEntryInformationSuccessfully_ForRegisteredUser() throws Exception {
		UserDTO userDTO = new UserDTO(EMAIL_ID);
		userDTO.setUserDataVersion(UserDataMigrator.CURRENT_USER_DATA_VERSION);
		CacheService cacheService = getMockedCacheService();
		UserDataDAO userDataDAO = new UserDataDAO(cacheService);
		userDataDAO.saveUser(userDTO);
		Key userKey = userDTO.getKey();

		EntryDAO entryDAO = new EntryDAO(cacheService);

		entryDAO.addNewEntry(userKey, getNewEntry(1));

		entryDAO.addNewEntry(userKey, getNewEntry(2));

		UserDTO newUserData = userDataDAO.getUserDataByKey(userKey);
		List<EntryDTO> entries = newUserData.getEntries();
		assertThat(entries.size(), is(2));
		EntryDTO entry0 = entries.get(0);
		EntryDTO entry1 = entries.get(1);
		assertCorrectness(entry0, 2);
		assertCorrectness(entry1, 1);

		verify(cacheService, times(2)).removeUser(any(Key.class));
	}

	@Test
	public void shouldEditEntryInformationSuccessfully_ForRegisteredUser() throws Exception {
		UserDTO userDTO = new UserDTO(EMAIL_ID);
		userDTO.setUserDataVersion(UserDataMigrator.CURRENT_USER_DATA_VERSION);
		CacheService cacheService = getMockedCacheService();
		UserDataDAO userDataDAO = new UserDataDAO(cacheService);
		userDataDAO.saveUser(userDTO);
		Key userKey = userDTO.getKey();

		EntryDAO entryDAO = new EntryDAO(cacheService);

		entryDAO.addNewEntry(userKey, getNewEntry(1));

		entryDAO.addNewEntry(userKey, getNewEntry(2));

		entryDAO.editEntry(userKey, 1, getNewEntry(3));

		UserDTO newUserData = userDataDAO.getUserDataByKey(userKey);
		List<EntryDTO> entries = newUserData.getEntries();
		assertThat(entries.size(), is(2));
		EntryDTO entry0 = entries.get(0);
		EntryDTO entry1 = entries.get(1);
		assertCorrectness(entry0, 2);
		assertCorrectness(entry1, 3);

		verify(cacheService, times(3)).removeUser(any(Key.class));
	}

	@Test
	public void shouldMoveEntryInformationSuccessfully_ForRegisteredUser() throws Exception {
		UserDTO userDTO = new UserDTO(EMAIL_ID);
		userDTO.setUserDataVersion(UserDataMigrator.CURRENT_USER_DATA_VERSION);
		CacheService cacheService = getMockedCacheService();
		UserDataDAO userDataDAO = new UserDataDAO(cacheService);
		userDataDAO.saveUser(userDTO);
		Key userKey = userDTO.getKey();

		EntryDAO entryDAO = new EntryDAO(cacheService);

		entryDAO.addNewEntry(userKey, getNewEntry(1));

		entryDAO.addNewEntry(userKey, getNewEntry(2));

		UserDTO userData = userDataDAO.getUserDataByKey(userKey);
		List<EntryDTO> entries = userData.getEntries();
		EntryDTO originalEntry0 = entries.get(0);
		EntryDTO originalEntry1 = entries.get(1);

		entryDAO.moveEntry(userKey, 0, "down");

		userData = userDataDAO.getUserDataByKey(userKey);
		entries = userData.getEntries();
		assertThat(entries.size(), is(2));
		EntryDTO entry0 = entries.get(0);
		EntryDTO entry1 = entries.get(1);
		assertThat(entry0.getKey(), is(originalEntry1.getKey()));
		assertThat(entry1.getKey(), is(originalEntry0.getKey()));

		verify(cacheService, times(3)).removeUser(any(Key.class));
	}

	@Test
	public void shouldDeleteEntryInformationSuccessfully_ForRegisteredUser() throws Exception {
		UserDTO userDTO = new UserDTO(EMAIL_ID);
		userDTO.setUserDataVersion(UserDataMigrator.CURRENT_USER_DATA_VERSION);
		CacheService cacheService = getMockedCacheService();
		UserDataDAO userDataDAO = new UserDataDAO(cacheService);
		userDataDAO.saveUser(userDTO);
		Key userKey = userDTO.getKey();

		EntryDAO entryDAO = new EntryDAO(cacheService);

		entryDAO.addNewEntry(userKey, getNewEntry(1));

		entryDAO.addNewEntry(userKey, getNewEntry(2));

		entryDAO.deleteEntry(userKey, 0);

		UserDTO userData = userDataDAO.getUserDataByKey(userKey);
		List<EntryDTO> entries = userData.getEntries();
		assertThat(entries.size(), is(1));
		EntryDTO entry0 = entries.get(0);
		assertCorrectness(entry0, 1);

		verify(cacheService, times(3)).removeUser(any(Key.class));
	}

	protected HttpServletRequest getMockedRequest(int counter) {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("ENTRY_TEXT")).thenReturn(SUMMARY + "_" + counter);
		return request;
	}

	protected EntryDTO getNewEntry(int counter) {
		EntryDTO entry = new EntryDTO();
		entry.setText(new Text(SUMMARY + "_" + counter));
		return entry;
	}

	protected void assertCorrectness(EntryDTO position, int counter) {
		assertThat(position.getText().getValue(), is(SUMMARY + "_" + counter));
	}
}
