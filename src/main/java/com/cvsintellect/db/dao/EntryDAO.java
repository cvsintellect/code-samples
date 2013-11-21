package com.cvsintellect.db.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.cvsintellect.common.util.DigestUtil;
import com.cvsintellect.db.cache.CacheService;
import com.cvsintellect.db.model.EntryDTO;
import com.cvsintellect.db.model.UserDTO;
import com.cvsintellect.servlet.util.GAELogger;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class EntryDAO extends AbstractDAO {
	private static final Logger LOG = Logger.getLogger(EntryDAO.class.getName());

	public EntryDAO(CacheService cacheService) {
		super(cacheService);
	}

	public String addNewEntry(Key userKey, EntryDTO newEntry) throws Exception {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction txn = pm.currentTransaction();
		String keyDigest = null;

		try {
			txn.begin();

			UserDTO userData = null;

			userData = pm.getObjectById(UserDTO.class, userKey);

			List<EntryDTO> entries = userData.getEntries();

			if (entries == null) {
				entries = new ArrayList<EntryDTO>();
				userData.setEntries(entries);
			}

			newEntry.setIndex(0);
			for (int i = 0; i < entries.size(); i++) {
				entries.get(i).setIndex(i + 1);
			}

			entries.add(newEntry);

			saveUser(pm, userData);

			keyDigest = DigestUtil.getDigestFor(KeyFactory.keyToString(newEntry.getKey()));
			newEntry.setKeyDigest(keyDigest);

			saveUser(pm, userData);

			txn.commit();

			removeUserFromCache(cacheService, userKey);
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

		return keyDigest;
	}

	public void editEntry(Key userKey, int index, EntryDTO editEntry) throws Exception {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction txn = pm.currentTransaction();

		try {
			txn.begin();

			UserDTO userData = null;

			userData = pm.getObjectById(UserDTO.class, userKey);

			List<EntryDTO> entries = userData.getEntries();

			EntryDTO entry = entries.get(index);

			entry.setText(editEntry.getText());

			saveUser(pm, userData);

			txn.commit();

			removeUserFromCache(cacheService, userKey);
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

	public void moveEntry(Key userKey, int index, String direction) throws Exception {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction txn = pm.currentTransaction();

		try {
			txn.begin();

			UserDTO userData = null;

			userData = pm.getObjectById(UserDTO.class, userKey);

			List<EntryDTO> positionList = userData.getEntries();

			if (direction.equalsIgnoreCase("up")) {
				positionList.get(index).setIndex(index - 1);
				positionList.get(index - 1).setIndex(index);
			}
			else if (direction.equalsIgnoreCase("down")) {
				positionList.get(index).setIndex(index + 1);
				positionList.get(index + 1).setIndex(index);
			}

			saveUser(pm, userData);

			txn.commit();

			removeUserFromCache(cacheService, userKey);
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

	public void deleteEntry(Key userKey, int index) throws Exception {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction txn = pm.currentTransaction();

		try {
			txn.begin();

			UserDTO userData = null;

			userData = pm.getObjectById(UserDTO.class, userKey);

			List<EntryDTO> positionList = userData.getEntries();

			for (int i = index + 1; i < positionList.size(); i++) {
				positionList.get(i).setIndex(i - 1);
			}
			EntryDTO deletePosition = positionList.get(index);
			positionList.remove(deletePosition);

			saveUser(pm, userData);

			txn.commit();

			removeUserFromCache(cacheService, userKey);
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
