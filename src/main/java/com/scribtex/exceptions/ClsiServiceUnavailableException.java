package com.scribtex.exceptions;

public class ClsiServiceUnavailableException extends ClsiServiceErrorException {

	private static final long serialVersionUID = 7319820983808354148L;

	public ClsiServiceUnavailableException() {
		super("The service is unavailable.");
	}
}
