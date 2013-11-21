package com.cvsintellect.db.dao;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.cvsintellect.db.cache.CacheService;
import com.cvsintellect.db.dao.migrator.UserDataMigrator;
import com.cvsintellect.db.model.UserDTO;
import com.google.appengine.api.datastore.Key;

public class UserDAOTest extends AbstractDAOTestHelper {
	private static final String EMAIL_ID = "first.lastname@test.com";

	@Test
	public void shouldSavePersonalInformationSuccessfully_ForRegisteredUser() throws Exception {
		UserDTO userDTO = new UserDTO(EMAIL_ID);
		userDTO.setUserDataVersion(UserDataMigrator.CURRENT_USER_DATA_VERSION);

		CacheService cacheService = getMockedCacheService();
		UserDataDAO userDataDAO = new UserDataDAO(cacheService);

		// save user
		userDataDAO.saveUser(userDTO);
		Key userKey = userDTO.getKey();
		assertThat(userKey, is(notNullValue()));

		// get by key
		UserDTO userByKey = userDataDAO.getUserDataByKey(userKey);
		assertThat(userByKey, is(notNullValue()));

		// check email does not exist
		try {
			userDataDAO.checkForUniqueEmailId(EMAIL_ID);
			fail("should have thrown exception");
		}
		catch (Exception e) {
		}

		// get by email
		UserDTO userByEmail = userDataDAO.getUserByEmailId(EMAIL_ID);
		assertEquals(userByKey, userByEmail);

		// delete user
		userDataDAO.deleteUser(userKey);

		try {
			userDataDAO.getUserDataByKey(userKey);
			fail("should have thrown exception");
		}
		catch (Exception e) {
		}
	}
}
