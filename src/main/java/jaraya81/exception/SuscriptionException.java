package jaraya81.exception;

import jaraya81.enums.ErrorCode;

public class SuscriptionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5386152754065090813L;

	public SuscriptionException(String message, ErrorCode errorCode) {
		super(message + " :: " + errorCode);
	}

	public SuscriptionException(Throwable e) {
		super(e);
	}

}
