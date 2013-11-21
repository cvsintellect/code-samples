package com.cvsintellect.db.model;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cvsintellect.common.util.DataValidationUtil;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class EntryDTO extends AbstractDTO implements Serializable, KeyDigestSearchable {
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String keyDigest;

	@Persistent
	private Integer index;

	@Persistent
	private Text text;

	public EntryDTO() {
		super();
	}

	public EntryDTO(Text text) {
		super();
		setText(text);
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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		if (DataValidationUtil.isEmpty(text))
			this.text = new Text("");
		else
			this.text = text;
	}
}
