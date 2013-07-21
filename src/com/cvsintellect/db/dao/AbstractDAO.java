package com.cvsintellect.db.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.cvsintellect.common.util.DigestUtil;
import com.cvsintellect.db.model.EntryDTO;
import com.cvsintellect.db.model.UserDTO;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.ji.rm.db.cache.CacheService;

public abstract class AbstractDAO {
	protected CacheService cacheService;

	public AbstractDAO(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	protected void saveUser(PersistenceManager pm, UserDTO userData) {
		pm.makePersistent(userData);
	}

	protected void removeUserFromCache(CacheService cacheService, Key userId) {
		cacheService.removeUser(userId);
	}

	public static void setKeyDigestForUser(UserDTO userDTO) {
		String keyDigest = DigestUtil.getDigestFor(KeyFactory.keyToString(userDTO.getKey()));
		userDTO.setKeyDigest(keyDigest);

		List<EntryDTO> entries = userDTO.getEntries();
		if (entries != null) {
			for (EntryDTO entry : entries) {
				keyDigest = DigestUtil.getDigestFor(KeyFactory.keyToString(entry.getKey()));
				entry.setKeyDigest(keyDigest);
			}
		}
	}
}
