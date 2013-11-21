package com.cvsintellect.db.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.cvsintellect.db.model.UserDTO;
import com.cvsintellect.servlet.util.GAELogger;
import com.google.appengine.api.capabilities.CapabilitiesService;
import com.google.appengine.api.capabilities.CapabilitiesServiceFactory;
import com.google.appengine.api.capabilities.Capability;
import com.google.appengine.api.capabilities.CapabilityStatus;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

public class CacheService {
	private static final Logger LOG = Logger.getLogger(CacheService.class.getName());

	private Cache cache;
	private Boolean enabled;

	public CacheService() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(GCacheFactory.EXPIRATION_DELTA, 3600);

		if (isCacheEnabled()) {
			try {
				CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
				cache = cacheFactory.createCache(properties);
			}
			catch (Exception e) {
				GAELogger.logError(LOG, e);
			}
		}
	}

	public boolean isCacheEnabled() {
		if (enabled == null) {
			CapabilitiesService service = CapabilitiesServiceFactory.getCapabilitiesService();
			CapabilityStatus cacheStatus = service.getStatus(Capability.MEMCACHE).getStatus();
			enabled = cacheStatus == CapabilityStatus.ENABLED;
		}
		return enabled;
	}

	protected String getCacheKeyForUser(Key userId) {
		String useKey = KeyFactory.keyToString(userId);
		return CacheKeys.USER_DATA_CACHE_KEY_.toString() + useKey;
	}

	protected String getCacheKeyForShardedCounterNumberOfShards(String name) {
		return CacheKeys.SHARDED_COUNTER_NUMBER_OF_SHARDS_CACHE_KEY_.toString() + name;
	}

	protected String getCacheKeyForShardedCounterTotalCount(String name) {
		return CacheKeys.SHARDED_COUNTER_TOTAL_COUNT_CACHE_KEY_.toString() + name;
	}

	public void putUser(UserDTO user) {
		if (isCacheEnabled() && cache != null) {
			try {
				String cacheKeyForUser = getCacheKeyForUser(user.getKey());
				cache.put(cacheKeyForUser, user);
			}
			catch (Exception e) {
				GAELogger.logError(LOG, e);
			}
		}
	}

	public UserDTO getUser(Key userId) {
		UserDTO user = null;
		if (isCacheEnabled() && cache != null) {
			try {
				String cacheKeyForUser = getCacheKeyForUser(userId);
				user = (UserDTO) cache.get(cacheKeyForUser);
			}
			catch (Exception e) {
				GAELogger.logError(LOG, e);
			}
		}
		return user;
	}

	public void removeUser(Key userId) {
		if (isCacheEnabled() && cache != null) {
			try {
				String cacheKeyForUser = getCacheKeyForUser(userId);
				cache.remove(cacheKeyForUser);
			}
			catch (Exception e) {
				GAELogger.logError(LOG, e);
			}
		}
	}

	public void saveShardedCounterNumberOfShards(String name, Integer value) {
		if (isCacheEnabled() && cache != null) {
			try {
				String cacheKeyForShardedCounterNumberOfShards = getCacheKeyForShardedCounterNumberOfShards(name);
				cache.put(cacheKeyForShardedCounterNumberOfShards, value);
			}
			catch (Exception e) {
				GAELogger.logError(LOG, e);
			}
		}
	}

	public Integer getShardedCounterNumberOfShards(String name) {
		if (isCacheEnabled() && cache != null) {
			try {
				String cacheKeyForShardedCounterNumberOfShards = getCacheKeyForShardedCounterNumberOfShards(name);
				return (Integer) cache.get(cacheKeyForShardedCounterNumberOfShards);
			}
			catch (Exception e) {
				GAELogger.logError(LOG, e);
			}
		}
		return null;
	}

	public void saveShardedCounterTotalCount(String name, Integer value) {
		if (isCacheEnabled() && cache != null) {
			try {
				String cacheKeyForShardedCounterTotalCount = getCacheKeyForShardedCounterTotalCount(name);
				cache.put(cacheKeyForShardedCounterTotalCount, value);
			}
			catch (Exception e) {
				GAELogger.logError(LOG, e);
			}
		}
	}

	public Integer getShardedCounterTotalCount(String name) {
		if (isCacheEnabled() && cache != null) {
			try {
				String cacheKeyForShardedCounterTotalCount = getCacheKeyForShardedCounterTotalCount(name);
				return (Integer) cache.get(cacheKeyForShardedCounterTotalCount);
			}
			catch (Exception e) {
				GAELogger.logError(LOG, e);
			}
		}
		return null;
	}
}
