package com.cvsintellect.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.cvsintellect.common.util.DataValidationUtil;
import com.cvsintellect.common.util.DigestUtil;
import com.cvsintellect.db.cache.CacheService;
import com.cvsintellect.db.dao.migrator.UserDataMigrator;
import com.cvsintellect.db.model.UserDTO;
import com.cvsintellect.servlet.util.GAELogger;
import com.cvsintellect.servlet.util.UserQuery;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

public class UserDataDAO extends AbstractDAO {
	private static final Logger LOG = Logger.getLogger(UserDataDAO.class.getName());

	public UserDataDAO(CacheService cacheService) {
		super(cacheService);
	}

	public Key createNewUser(String emailId) throws Exception {
		checkForUniqueEmailId(emailId);

		UserDTO newUserData = new UserDTO(emailId);

		return saveUser(newUserData);
	}

	void checkForUniqueEmailId(String emailId) throws Exception {
		emailId = emailId.toLowerCase().trim();
		if (hasUserByEmailId(emailId)) {
			throw new RuntimeException("user with emailid exists: " + emailId);
		}
	}

	Key saveUser(UserDTO userDTO) throws Exception {
		Key userId = null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction txn = pm.currentTransaction();

		try {
			txn.begin();

			pm.makePersistent(userDTO);

			String keyDigest = DigestUtil.getDigestFor(KeyFactory.keyToString(userDTO.getKey()));
			userDTO.setKeyDigest(keyDigest);

			pm.makePersistent(userDTO);

			txn.commit();

			userId = userDTO.getKey();
		}
		catch (Exception e) {
			GAELogger.logError(LOG, e);
			throw e;
		}
		finally {
			if (txn.isActive()) {
				txn.rollback();
			}
			pm.close();
		}

		return userId;
	}

	public UserDTO getUserDataByKey(Key userId) throws Exception {
		UserDTO user = cacheService.getUser(userId);

		if (user != null)
			return user;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			user = pm.getObjectById(UserDTO.class, userId);

			if (user.getUserDataVersion() < UserDataMigrator.CURRENT_USER_DATA_VERSION) {
				new UserDataMigrator().migrate(user.getKey());
				user = pm.getObjectById(UserDTO.class, userId);
			}

			// lazy loading of data
			if (user.getEntries() != null) {
				user.getEntries().size();
			}

			cacheService.putUser(user);
		}
		catch (Exception e) {
			GAELogger.logError(LOG, e);
			throw e;
		}
		finally {
			pm.close();
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public boolean hasUserByEmailId(String emailId) throws Exception {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(UserDTO.class, "username == :username");

		try {
			List<UserDTO> users = (List<UserDTO>) q.execute(emailId);
			if (users != null && !users.isEmpty()) {
				return true;
			}
		}
		catch (Exception e) {
			GAELogger.logError(LOG, e);
			throw e;
		}
		finally {
			q.closeAll();
			pm.close();
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public UserDTO getUserByEmailId(String emailId) throws Exception {
		UserDTO user = null;

		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(UserDTO.class, "username == :username");

		try {
			List<UserDTO> users = (List<UserDTO>) q.execute(emailId);
			if (users != null && !users.isEmpty()) {
				user = users.get(0);
				user = getUserDataByKey(user.getKey());
			}
		}
		catch (Exception e) {
			GAELogger.logError(LOG, e);
			throw e;
		}
		finally {
			q.closeAll();
			pm.close();
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	public void getUsersViaPagination(UserQuery userQuery, int current, int pageSize) throws Exception {
		Cursor cursor = null;

		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(UserDTO.class);

		try {
			if (!DataValidationUtil.isEmpty(userQuery.getUserCursor(current))) {
				cursor = Cursor.fromWebSafeString(userQuery.getUserCursor(current));
				Map<String, Object> extensions = new HashMap<String, Object>();
				extensions.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				q.setExtensions(extensions);
				q.setRange(0, pageSize);
			}
			else
				q.setRange(0, pageSize);

			List<UserDTO> users = (List<UserDTO>) q.execute();

			cursor = JDOCursorHelper.getCursor(users);
			if (cursor == null || users == null || users.size() < pageSize)
				userQuery.setNoMoreUserResults(true);
			else
				userQuery.addUserCursor(current + 1, cursor.toWebSafeString());

			List<UserDTO> detachedResults = (List<UserDTO>) pm.detachCopyAll(users);

			userQuery.setUsers(detachedResults);
		}
		catch (Exception e) {
			GAELogger.logError(LOG, e);
			throw e;
		}
		finally {
			q.closeAll();
			pm.close();
		}
	}

	public void deleteUser(Key userKey) throws Exception {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction txn = pm.currentTransaction();

		try {
			txn.begin();

			UserDTO userToDelete = pm.getObjectById(UserDTO.class, userKey);
			pm.deletePersistent(userToDelete);

			txn.commit();

			cacheService.removeUser(userKey);
		}
		catch (Exception e) {
			GAELogger.logError(LOG, e);
			throw e;
		}
		finally {
			if (txn.isActive()) {
				txn.rollback();
			}
			pm.close();
		}
	}
}
