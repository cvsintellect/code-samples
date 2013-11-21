package com.cvsintellect.db.model;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cvsintellect.db.dao.migrator.UserDataMigrator;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class UserDTO extends AbstractDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String keyDigest;

	@Persistent
	private int userDataVersion;

	@Persistent
	private String username;

	@Persistent
	@Element(dependent = "true")
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "index ASC"))
	private List<EntryDTO> entries;

	public UserDTO(String username) {
		super();
		this.username = username;
		this.userDataVersion = UserDataMigrator.CURRENT_USER_DATA_VERSION;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getKeyDigest() {
		return keyDigest;
	}

	public void setKeyDigest(String keyDigest) {
		this.keyDigest = keyDigest;
	}

	public int getUserDataVersion() {
		return userDataVersion;
	}

	public void setUserDataVersion(int userDataVersion) {
		this.userDataVersion = userDataVersion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<EntryDTO> getEntries() {
		return entries;
	}

	public void setEntries(List<EntryDTO> entries) {
		this.entries = entries;
	}
}
