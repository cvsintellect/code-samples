package com.scribtex.exceptions;

public class ClsiServiceFormatException extends ClsiServiceErrorException {

	private static final long serialVersionUID = 7319820983808354148L;

	public ClsiServiceFormatException() {
		super("Invalid XML format.");
	}
}
