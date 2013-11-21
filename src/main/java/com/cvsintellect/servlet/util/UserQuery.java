package com.cvsintellect.servlet.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cvsintellect.db.model.UserDTO;

public class UserQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<String> userCursors;
	private boolean noMoreUserResults;
	private List<UserDTO> users;

	public UserQuery() {
		userCursors = new ArrayList<String>();
		userCursors.add("");
	}

	public List<String> getUserCursors() {
		return userCursors;
	}

	public void setUserCursors(List<String> cursors) {
		this.userCursors = cursors;
	}

	public void addUserCursor(int index, String cursor) {
		if (index == userCursors.size())
			userCursors.add(cursor);
	}

	public String getUserCursor(int current) {
		if (current <= 0 || current > userCursors.size())
			return null;
		return userCursors.get(current);
	}

	public boolean hasNoMoreUserResults() {
		return noMoreUserResults;
	}

	public void setNoMoreUserResults(boolean noMoreUserResults) {
		this.noMoreUserResults = noMoreUserResults;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
}
