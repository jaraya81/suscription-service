package cl.jaraya81.exception;

import cl.jaraya81.enums.ErrorCode;

public class SubscriptionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5386152754065090813L;

	public SubscriptionException(String message, ErrorCode errorCode) {
		super(message + " :: " + errorCode);
	}

	public SubscriptionException(Throwable e) {
		super(e);
	}

}
