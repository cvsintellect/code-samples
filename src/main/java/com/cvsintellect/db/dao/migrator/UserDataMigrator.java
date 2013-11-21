package com.cvsintellect.db.dao.migrator;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.cvsintellect.db.dao.PMF;
import com.cvsintellect.db.model.UserDTO;
import com.cvsintellect.servlet.util.GAELogger;
import com.google.appengine.api.datastore.Key;

public class UserDataMigrator {
	private static final Logger LOG = Logger.getLogger(UserDataMigrator.class.getName());

	public static final int CURRENT_USER_DATA_VERSION = 1;

	public void migrate(Key userId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction txn = pm.currentTransaction();

		try {
			txn.begin();

			UserDTO userData = pm.getObjectById(UserDTO.class, userId);

			migrateTo1(userData);

			pm.makePersistent(userData);

			txn.commit();
		}
		catch (Exception e) {
			GAELogger.logError(LOG, e);
		}
		finally {
			if (txn.isActive()) {
				txn.rollback();
			}
			pm.close();
		}
	}

	private void migrateTo1(UserDTO userDTO) {
		if (userDTO.getUserDataVersion() == 0) {
			userDTO.setUserDataVersion(1);
		}
	}
}
